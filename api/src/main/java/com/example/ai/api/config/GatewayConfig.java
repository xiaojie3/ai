package com.example.ai.api.config;

import com.example.ai.api.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {
    private final AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // ======================== 认证服务路由 ========================
                .route("auth-service-route", r -> r.path("/api/auth/**") // 1. 匹配所有以 /api/auth/ 开头的请求
                        .filters(f -> f
                                // 2. (重要) 在这里不要应用 authenticationFilter
                                //    因为登录请求本身就是为了获取 Token，此时客户端还没有 Token
                                .rewritePath("/api/auth/(?<segment>.*)", "/auth/${segment}") // 3. 路径重写，去掉 /api 前缀
                        )
                        .uri("http://localhost:8081") // 4. 转发到 Auth Service 的地址
                )
                // ======================== 业务服务路由 ========================
                .route("system-service-route", r -> r.path("/api/system/**")
                        .filters(f -> f
                                .filter(authenticationFilter) // 对业务服务应用认证过滤器
                                .rewritePath("/api/system/(?<segment>.*)", "/system/${segment}")
                        )
                        .uri("http://localhost:8082")
                )
                .route("resources-service-route", r -> r.path("/api/resources/**")
                        .filters(f -> f
                                .filter(authenticationFilter) // 对业务服务应用认证过滤器
                                .rewritePath("/api/resources/(?<segment>.*)", "/resources/${segment}")
                        )
                        .uri("http://localhost:8083")
                )
                .build();
    }
}