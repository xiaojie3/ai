package com.example.edu.authservice.config;

import com.example.edu.authservice.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // UserDetailsService 和 PasswordEncoder 会被 Spring Security 自动发现和使用
    // 你只需要确保它们在 Spring 容器中被定义为 Bean (例如，在 UserDetailsServiceImpl 上使用 @Service)

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // 必须放行 /error 端点，否则会导致无限循环
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
        // 关键配置 1: 自定义认证失败入口点
                .exceptionHandling(ex -> ex
                .authenticationEntryPoint(authenticationEntryPoint())
                // 关键配置 2: 自定义权限不足处理器
                .accessDeniedHandler(accessDeniedHandler())
        );

        return http.build();
    }

    /**
     * 自定义认证失败入口点
     * 当用户未认证时，Spring Security 会调用这里的方法
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            // 我们不直接在这里写响应，而是抛出一个自定义异常
            // 这个异常会被我们的 GlobalExceptionHandler 捕获
            throw new UnauthorizedException("未登录，无法访问资源！");
        };
    }

    /**
     * 自定义权限不足处理器
     * 当用户已认证但权限不足时，Spring Security 会调用这里的方法
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            // 同样，抛出一个自定义异常，交由 GlobalExceptionHandler 处理
            throw new AccessDeniedException("权限不足！");
        };
    }
}
