package com.example.demo.resources.config;

import com.example.demo.resources.interceptor.UserIdInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserIdInterceptor())
                .addPathPatterns("/resources/**"); // 对所有请求生效
        // .excludePathPatterns("/login", "/register"); // 可以排除一些公开接口
    }
}
