package com.example.demo.auth.service.impl;

import com.example.demo.auth.model.RefreshToken;
import com.example.demo.auth.model.dto.LoginDto;
import com.example.demo.auth.repository.RefreshTokenRepository;
import com.example.demo.auth.service.AuthService;
import com.example.demo.auth.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshTokenRepository refreshTokenRepository;

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
        UserDetails user = userDetailsService.loadUserByUsername(account);

        // 4. 使用 JwtService 生成访问令牌和刷新令牌
        String accessToken = jwtTokenUtil.generateAccessToken(user);
        String refreshToken = jwtTokenUtil.generateRefreshToken(user);

        // 5. 保存 Refresh Token 到数据库
        // 可以先删除该用户之前所有的 Refresh Token，以实现单点登录
        refreshTokenRepository.deleteByAccount(user.getUsername());

        RefreshToken authRefreshToken = new RefreshToken();
        authRefreshToken.setAccount(user.getUsername());
        authRefreshToken.setRefreshToken(refreshToken);
        // 计算过期时间点 (Instant)
        Instant expirationInstant = Instant.now().plusMillis(jwtTokenUtil.getRefreshTokenExpiration());
        // 转换为 LocalDateTime (使用系统默认时区，可以指定时区，如 ZoneId.of("UTC"))
        LocalDateTime expiresLocalDateTime = expirationInstant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        authRefreshToken.setExpiresTime(expiresLocalDateTime);
        refreshTokenRepository.save(authRefreshToken);

        // 6. 构建并返回 LoginResponse DTO
        return new LoginDto(accessToken, refreshToken, "Bearer", jwtTokenUtil.getAccessTokenExpiration());
    }

    @Override
    public LoginDto refreshToken(String refreshToken) {
        // 1. 从数据库中查找并验证 Refresh Token
        RefreshToken storedToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        // --- 重点修改部分 ---
        // 2. 检查 Token 是否过期 (使用 LocalDateTime.now() 进行比较)
        if (storedToken.getExpiresTime().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(storedToken);
            throw new RuntimeException("Refresh token expired");
        }

        // 3. 如果有效，加载用户信息并生成新的 Access Token
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtTokenUtil.getAccountFromToken(refreshToken));
        String newAccessToken = jwtTokenUtil.generateAccessToken(userDetails);

        return new LoginDto(newAccessToken, refreshToken, "Bearer", jwtTokenUtil.getAccessTokenExpiration());
    }

    @Override
    public void logout(String token) {
        String account = jwtTokenUtil.getAccountFromToken(token);
        refreshTokenRepository.deleteByAccount(account);
    }
}
