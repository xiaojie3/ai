package com.example.demo.auth.handler;

import com.example.demo.common.model.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 声明这是一个控制器建言，用于处理异常
public class GlobalExceptionHandler {
    /**
     * 处理未认证异常 (401 Unauthorized)
     * 例如: 没有携带Token, Token无效, Token已过期
     */
    @ExceptionHandler({InsufficientAuthenticationException.class, BadCredentialsException.class})
    public ResponseEntity<ApiResult<String>> handleUnauthorizedException(Exception ex, HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResult<>(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(), // 使用异常自带的消息
                request.getRequestURI()
        ), HttpStatus.UNAUTHORIZED);
    }

    /**
     * 处理权限不足异常 (403 Forbidden)
     * 例如: 用户已登录，但没有访问该资源的权限
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResult<String>> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        ApiResult<String> error = new ApiResult<>(
                HttpStatus.FORBIDDEN.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    /**
     * 处理其他所有未捕获的异常 (500 Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResult<String>> handleAllExceptions(Exception ex, HttpServletRequest request) {
        ApiResult<String> error = new ApiResult<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        // 在开发环境，可以打印出详细的错误堆栈信息
        ex.fillInStackTrace();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
