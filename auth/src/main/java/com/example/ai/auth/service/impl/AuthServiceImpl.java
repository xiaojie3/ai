package com.example.ai.auth.service.impl;

import com.example.ai.auth.dto.LoginDto;
import com.example.ai.auth.service.AuthService;
import com.example.ai.common.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userService;
    @Override
    public LoginDto login(String account, String password) {
        // 1. 使用 AuthenticationManager 进行认证
        // UsernamePasswordAuthenticationToken 是一个包含用户名和密码的认证请求对象
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        account,
                        password
                )
        );

        // 2. 将认证成功的结果存入 SecurityContext
        // 这一步是可选的，但在某些情况下（如需要在当前线程中获取用户信息）很有用
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. 认证成功后，加载完整的用户信息
        UserDetails user = userService.loadUserByUsername(account);

        // 4. 使用 JwtService 生成访问令牌和刷新令牌
        String accessToken = JwtTokenUtil.generateToken(user);
        String refreshToken = JwtTokenUtil.generateRefreshToken(user);

        // 5. 构建并返回 LoginResponse DTO
        return new LoginDto(accessToken, refreshToken, "Bearer", JwtTokenUtil.expiration);
    }
}
