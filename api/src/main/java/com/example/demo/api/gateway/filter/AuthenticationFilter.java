package com.example.demo.api.gateway.filter;

import com.alibaba.fastjson2.JSONObject;
import com.example.demo.common.model.ApiResult;
import com.example.demo.common.util.JwtTokenUtil;
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

@Component
@Order(-1)
public class AuthenticationFilter implements GatewayFilter {

    private String extractToken(ServerHttpRequest request) {
        return request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        // 返回一个包含错误信息的 JSON 响应
        ApiResult<String> responseBody = ApiResult.error(HttpStatus.UNAUTHORIZED.value(), "认证令牌无效或已过期");
        response.getHeaders().add("Content-Type", "application/json");
        return response.writeWith(Mono.just(response.bufferFactory().wrap(JSONObject.toJSONString(responseBody).getBytes())));
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 1. 检查是否是公开接口（如登录、注册），如果是则直接放行
        // 这里可以用一个列表或配置来管理白名单
        String path = request.getPath().value();
        if (path.startsWith("/api/auth/login") || path.startsWith("/api/auth/register") || path.startsWith("/api/auth/refresh-token")) {
            return chain.filter(exchange);
        }
        try {
            JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
            String token = extractToken(request);
            String userId = new JwtTokenUtil().getUserIdFromToken(token);
            // 认证令牌是否过期
            if(!jwtTokenUtil.validateToken(token, userId)) {
                return onError(exchange);
            }
            // 创建一个新地请求，将用户信息添加到 Header
            ServerHttpRequest mutatedRequest = request.mutate()
                    .header("X-UserId", userId)
                    .build();
            // 5. 将修改后的请求继续传递给下一个过滤器或目标服务
            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        } catch (Exception e) {
            // 6. Token 验证失败（过期、签名错误等）
            e.fillInStackTrace();
            return onError(exchange);
        }
    }
}
