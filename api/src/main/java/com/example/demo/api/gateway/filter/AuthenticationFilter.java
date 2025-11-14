package com.example.demo.api.gateway.filter;

import com.alibaba.fastjson2.JSONObject;
import com.example.ai.common.model.ApiResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
@Order(-1)
public class AuthenticationFilter implements GatewayFilter {

    // 从配置文件读取 JWT 密钥，必须与 Auth Service 中的密钥一致
    @Value("${jwt.secret}")
    private String jwtSecret;

    private String extractToken(ServerHttpRequest request) {
        return request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        // 返回一个包含错误信息的 JSON 响应
        ApiResult<String> responseBody = ApiResult.error(HttpStatus.UNAUTHORIZED.value(), err);
        response.getHeaders().add("Content-Type", "application/json");
        return response.writeWith(Mono.just(response.bufferFactory().wrap(JSONObject.toJSONString(responseBody).getBytes())));
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 1. 检查是否是公开接口（如登录、注册），如果是则直接放行
        // 这里可以用一个列表或配置来管理白名单
        String path = request.getPath().value();
        if (path.startsWith("/api/auth/login") || path.startsWith("/api/auth/register")) {
            return chain.filter(exchange);
        }

        // 2. 从 Header 中获取 Token
        String token = extractToken(request);
        if (token == null || !token.startsWith("Bearer ")) {
            return onError(exchange, "未提供有效的认证令牌");
        }
        token = token.substring(7); // 去掉 "Bearer " 前缀

        try {
            // 3. 验证 Token
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // 4. Token 验证成功，提取用户信息并添加到请求头中
            String account = claims.getSubject();

            // 创建一个新的请求，将用户信息添加到 Header
            ServerHttpRequest mutatedRequest = request.mutate()
                    .header("X-Account", account)
                    .build();

            // 5. 将修改后的请求继续传递给下一个过滤器或目标服务
            return chain.filter(exchange.mutate().request(mutatedRequest).build());

        } catch (Exception e) {
            // 6. Token 验证失败（过期、签名错误等）
            return onError(exchange, "认证令牌无效或已过期");
        }
    }
}
