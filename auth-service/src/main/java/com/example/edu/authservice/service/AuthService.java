package com.example.edu.authservice.service;

import com.example.edu.authservice.dto.LoginRequest;
import com.example.edu.authservice.dto.LoginResponse;
import com.example.edu.authservice.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userService;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest loginRequest) {
        // 1. 使用 AuthenticationManager 进行认证
        // UsernamePasswordAuthenticationToken 是一个包含用户名和密码的认证请求对象
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getAccount(),
                        loginRequest.getPassword()
                )
        );

        // 2. 将认证成功的结果存入 SecurityContext
        // 这一步是可选的，但在某些情况下（如需要在当前线程中获取用户信息）很有用
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. 认证成功后，加载完整的用户信息
        UserDetails user = userService.loadUserByUsername(loginRequest.getAccount());

        // 4. 使用 JwtService 生成访问令牌和刷新令牌
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // 5. 构建并返回 LoginResponse DTO
        return new LoginResponse(accessToken, refreshToken, "Bearer", jwtService.getExpiration());
    }
}
