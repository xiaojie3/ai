package com.example.demo.auth.controller;

import com.example.demo.auth.model.dto.LoginResponse;
import com.example.demo.auth.model.dto.UserDetailsDto;
import com.example.demo.auth.service.AuthService;
import com.example.demo.common.model.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth") // 统一的路由前缀
@RequiredArgsConstructor
public class AuthController {

    private final MessageSource messageSource;
    private final AuthService authService;

    @PostMapping("/login") // 登录接口
    public ApiResult<LoginResponse> login(@RequestBody UserDetailsDto userDetailsDto) {
        return ApiResult.success(authService.login(userDetailsDto.getUsername(), userDetailsDto.getPassword()));
    }

    @PostMapping("/refresh-token")
    public ApiResult<LoginResponse> refreshToken(@RequestBody LoginResponse loginDto) {
        return ApiResult.success(authService.refreshToken(loginDto.getRefreshToken()));
    }

    @PostMapping("/logout")
    public ApiResult<String> logoutUser(HttpServletRequest request) {
        // 1. 从请求头中提取 Token
        String token = request.getHeader("Authorization");
        if(token != null) {
            authService.logout(token);
        }
        String msg = messageSource.getMessage("logout.success", null, LocaleContextHolder.getLocale());
        return ApiResult.success(null, msg);
    }
}