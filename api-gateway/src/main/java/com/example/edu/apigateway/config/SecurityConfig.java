package com.example.edu.apigateway.config;

import com.example.edu.apigateway.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // API网关通常禁用CSRF
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance()) // 无状态
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/auth/**").permitAll() // 认证相关接口放行
                        .pathMatchers("/public/**").permitAll() // 公开接口放行
                        .anyExchange().authenticated() // 其他所有请求都需要认证
                )
                .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION) // 添加自定义JWT认证过滤器
                .build();
    }
}
