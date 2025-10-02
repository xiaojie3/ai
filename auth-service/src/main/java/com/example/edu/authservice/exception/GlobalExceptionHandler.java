package com.example.edu.authservice.exception;

import com.example.edu.authservice.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 声明这是一个控制器建言，用于处理异常
public class GlobalExceptionHandler {

    /**
     * 处理未认证异常 (401 Unauthorized)
     * 例如: 没有携带Token, Token无效, Token已过期
     */
    @ExceptionHandler({InsufficientAuthenticationException.class, UnauthorizedException.class})
    public ResponseEntity<ApiError> handleUnauthorizedException(Exception ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage(), // 使用异常自带的消息
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 处理权限不足异常 (403 Forbidden)
     * 例如: 用户已登录，但没有访问该资源的权限
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                HttpStatus.FORBIDDEN,
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    /**
     * 处理其他所有未捕获的异常 (500 Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllExceptions(Exception ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getRequestURI()
        );
        // 在开发环境，可以打印出详细的错误堆栈信息
        ex.fillInStackTrace();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
