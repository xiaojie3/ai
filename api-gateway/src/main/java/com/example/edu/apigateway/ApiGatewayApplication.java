package com.example.edu.apigateway;

import com.example.edu.apigateway.filter.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    private final AuthorizationFilter authorizationFilter;

    public ApiGatewayApplication(AuthorizationFilter authorizationFilter) {
        this.authorizationFilter = authorizationFilter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        // 定义课程服务路由所需的权限
        List<String> coursePermissions = Arrays.asList("read:course", "create:course");

        // 定义作业服务路由所需的权限
        List<String> assignmentPermissions = List.of("submit:assignment");

        return builder.routes()
                .route("auth-service", r -> r
                        .path("/api/v1/auth/**")
                        .uri("lb://auth-service")
                )
                .route("course-service", r -> r
                        .path("/api/v1/courses/**")
                        .filters(f -> f
                                .filter(authorizationFilter.apply(new AuthorizationFilter.Config(coursePermissions)))
                        )
                        .uri("lb://course-service")
                )
                .route("assignment-service", r -> r
                        .path("/api/v1/assignments/submit/**")
                        .filters(f -> f
                                .filter(authorizationFilter.apply(new AuthorizationFilter.Config(assignmentPermissions)))
                        )
                        .uri("lb://assignment-service")
                )
                .build();
    }
}
