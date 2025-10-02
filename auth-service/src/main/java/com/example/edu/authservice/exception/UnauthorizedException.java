package com.example.edu.authservice.exception;
import org.springframework.security.core.AuthenticationException;

// 用于处理未认证的情况 (例如: 没有提供Token, Token无效)
public class UnauthorizedException extends AuthenticationException {
    public UnauthorizedException(String msg) {
        super(msg);
    }
}