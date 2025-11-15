package com.example.demo.auth.service.impl;

import com.example.demo.auth.model.dto.LoginResponse;
import com.example.demo.auth.model.entity.RefreshToken;
import com.example.demo.auth.model.entity.User;
import com.example.demo.auth.repository.RefreshTokenRepository;
import com.example.demo.auth.service.AuthService;
import com.example.demo.auth.service.UserService;
import com.example.demo.auth.util.JwtTokenUtil;
import com.example.demo.common.util.MyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MessageSource messageSource;

    @Override
    @Transactional
    public LoginResponse login(String account, String password) {
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
        User user = userService.findByAccount(account);

        // 4. 使用 JwtService 生成访问令牌和刷新令牌
        String accessToken = jwtTokenUtil.generateAccessToken(user.getId());
        String refreshToken = jwtTokenUtil.generateRefreshToken(user.getId());

        // 5. 保存 Refresh Token 到数据库
        // 可以先删除该用户之前所有的 Refresh Token，以实现单点登录
        refreshTokenRepository.deleteByAccount(user.getUsername());

        RefreshToken authRefreshToken = new RefreshToken();
        authRefreshToken.setId(MyUtils.getUUID());
        authRefreshToken.setAccount(user.getUsername());
        authRefreshToken.setRefreshToken(refreshToken);
        // 计算过期时间点 (Instant)
        Instant expirationInstant = Instant.now().plusMillis(jwtTokenUtil.getRefreshTokenExpiration());
        // 转换为 LocalDateTime (使用系统默认时区，可以指定时区，如 ZoneId.of("UTC"))
        LocalDateTime expiresLocalDateTime = expirationInstant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        authRefreshToken.setExpiresTime(expiresLocalDateTime);
        authRefreshToken.setCreateTime(LocalDateTime.now());
        refreshTokenRepository.save(authRefreshToken);

        // 6. 构建并返回 LoginResponse DTO
        return new LoginResponse(accessToken, refreshToken, jwtTokenUtil.getAccessTokenExpiration());
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {
        // 1. 从数据库中查找并验证 Refresh Token
        RefreshToken storedToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException(messageSource.getMessage("refresh-token.invalid", null, LocaleContextHolder.getLocale())));

        // --- 重点修改部分 ---
        // 2. 检查 Token 是否过期 (使用 LocalDateTime.now() 进行比较)
        if (storedToken.getExpiresTime().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(storedToken);
            throw new RuntimeException("refresh-token.expired");
        }

        // 3. 如果有效，加载用户信息并生成新的 Access Token
        User user = userService.findById(jwtTokenUtil.getUserIdFromToken(refreshToken));
        String newAccessToken = jwtTokenUtil.generateAccessToken(user.getId());

        return new LoginResponse(newAccessToken, refreshToken, jwtTokenUtil.getAccessTokenExpiration());
    }

    @Override
    public void logout(String token) {
        String account = jwtTokenUtil.getUserIdFromToken(token);
        refreshTokenRepository.deleteByAccount(account);
    }
}
