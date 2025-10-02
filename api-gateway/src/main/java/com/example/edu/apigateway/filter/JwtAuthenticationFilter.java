package com.example.edu.apigateway.filter;

import com.example.edu.apigateway.util.JwtUtil;
import com.example.edu.common.dto.UserContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

// 1. 实现 WebFilter 接口
@Component
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    private final WebClient.Builder webClientBuilder;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, WebClient.Builder webClientBuilder) {
        this.jwtUtil = jwtUtil;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // 2. 从请求头中获取Authorization信息
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // 3. 检查Authorization头是否存在且以"Bearer "开头
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // 如果不是，直接放行，让后续的Security配置(如 .anyExchange().authenticated())来处理未认证的请求
            // 这样可以允许公开接口(permitAll)通过
            return chain.filter(exchange);
        }

        String token = authHeader.substring(7);

        // 4. 验证JWT令牌的有效性
        if (!jwtUtil.validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String username = jwtUtil.extractUsername(token);

        // 5. 调用 auth-service 获取用户的详细权限信息
        return webClientBuilder.build()
                .get()
                .uri("http://auth-service/api/v1/auth/user-permissions/" + username)
                .retrieve()
                .bodyToMono(UserContext.class)
                .flatMap(userContext -> {
                    // 6. 将从auth-service获取的权限转换为Spring Security的GrantedAuthority
                    List<SimpleGrantedAuthority> authorities = userContext.getPermissions().stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    // 7. 创建Authentication对象并设置到ReactiveSecurityContext中
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            userContext.getUsername(), null, authorities);

                    // 8. 将SecurityContext写入上下文，并继续执行过滤链
                    return chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
                })
                .onErrorResume(e -> {
                    // 如果调用auth-service失败或发生其他错误，也视为认证失败
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
    }
}
