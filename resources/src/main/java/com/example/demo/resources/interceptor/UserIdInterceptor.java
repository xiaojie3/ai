package com.example.demo.resources.interceptor;

import com.example.demo.common.context.UserContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.AccessDeniedException;

public class UserIdInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(UserIdInterceptor.class);
    private static final String HEADER_X_USER_ID = "X-UserId";

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        // 1. 从请求头中获取 X-UserId
        String userId = request.getHeader(HEADER_X_USER_ID);

        // 2. 将 UserId 存入 ThreadLocal
        if (userId != null && !userId.trim().isEmpty()) {
            UserContextHolder.setUserId(userId);
            logger.debug("Successfully extracted and set X-UserId: {}", userId);
        } else {
            // 根据业务需求处理：可以警告、返回错误或忽略
            logger.warn("X-UserId header is missing or empty.");
            // 如果是必须的，可以在这里抛出异常或返回 401/403
            throw new AccessDeniedException("X-UserId is required");
        }

        // 3. 返回 true 表示继续执行后续的拦截器和 Controller
        return true;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) {
        // 视图渲染之后执行，这里通常不需要操作
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        // 请求完成后（无论成功或失败），清除 ThreadLocal，防止内存泄漏
        UserContextHolder.clear();
        logger.debug("Cleared UserContextHolder for request.");
    }
}
