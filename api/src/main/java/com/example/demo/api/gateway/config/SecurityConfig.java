package com.example.demo.api.gateway.config;

import com.example.demo.common.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    public JwtTokenUtil getJwtTokenUtil() {
        if(jwtSecret == null) {
            return new JwtTokenUtil();
        } else {
            return new JwtTokenUtil(jwtSecret);
        }
    }
}
