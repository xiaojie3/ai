package com.example.demo.auth.filter;

import com.example.demo.auth.model.entity.User;
import com.example.demo.auth.service.UserService;
import com.example.demo.auth.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    /**
     * 核心拦截逻辑：验证 JWT 令牌
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 从请求头中获取 Token
        String token = request.getHeader("Authorization");
        String userId = null;
        if (token != null) {
            try {
                userId = jwtTokenUtil.getUserIdFromToken(token); // 从令牌获取用户名
            } catch (Exception e) {
                // 令牌解析失败（过期、签名错误等）
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write("{\"code\":401,\"msg\":\"令牌无效或已过期\"}");
                return;
            }
        }

        // 2. 用户名不为空，且 Security 上下文未认证
        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 3. 从数据库加载用户信息（UserDetails）
            User user = userService.findById(userId);

            // 4. 验证令牌有效性
            if (jwtTokenUtil.validateToken(token, user.getId())) {
                // 5. 构建认证令牌，存入 Security 上下文（后续权限判断会从这里获取用户角色/权限）
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // 令牌验证失败
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write("{\"code\":401,\"msg\":\"令牌验证失败\"}");
                return;
            }
        }

        // 自定义过滤逻辑，例如记录请求日志
        System.out.println("Request URI: " + request.getRequestURI());

        // 6. 令牌验证通过，继续执行后续过滤器
        filterChain.doFilter(request, response);
    }
}
