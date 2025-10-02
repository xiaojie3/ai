package com.example.edu.apigateway.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

    public AuthorizationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            return ReactiveSecurityContextHolder.getContext()
                    .flatMap(securityContext -> {
                        Authentication authentication = securityContext.getAuthentication();
                        if (authentication == null) {
                            exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.FORBIDDEN);
                            return exchange.getResponse().setComplete();
                        }

                        Set<String> userPermissions = authentication.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toSet());

                        // 检查用户是否拥有访问所需的任何一个权限
                        boolean hasPermission = config.getRequiredPermissions().stream()
                                .anyMatch(userPermissions::contains);

                        if (hasPermission) {
                            return chain.filter(exchange);
                        } else {
                            exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.FORBIDDEN);
                            return exchange.getResponse().setComplete();
                        }
                    });
        };
    }

    @Setter
    @Getter
    public static class Config {
        private List<String> requiredPermissions;

        public Config(List<String> assignmentPermissions) {
            this.requiredPermissions = assignmentPermissions;
        }

    }
}