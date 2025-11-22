package com.example.demo.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.auth.model.dto.LoginResponse;
import com.example.demo.auth.model.dto.UserInfoDTO;
import com.example.demo.auth.model.entity.RefreshToken;
import com.example.demo.auth.model.entity.User;
import com.example.demo.auth.service.AuthService;
import com.example.demo.auth.service.RefreshTokenService;
import com.example.demo.auth.service.RoleService;
import com.example.demo.auth.service.UserService;
import com.example.demo.common.util.JwtTokenUtil;
import com.example.demo.common.util.MyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshTokenService refreshTokenService;
    private final MessageSource messageSource;

    @Override
    @Transactional
    public LoginResponse login(String account, String password) {
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, account));

        if (user == null) {
            throw new UsernameNotFoundException(messageSource.getMessage("login.account.not.exists", null, LocaleContextHolder.getLocale()));
        }

        boolean passwordMatch = passwordEncoder.matches(password, user.getPassword());
        if (!passwordMatch) {
            throw new BadCredentialsException(messageSource.getMessage("login.password.error", null, LocaleContextHolder.getLocale()));
        }

        // 3. 额外校验（可选）：账号状态（禁用、锁定等）
        if (!user.isEnabled()) {
            throw new DisabledException(messageSource.getMessage("login.account.locked", null, LocaleContextHolder.getLocale()));
        }

        // 4. 使用 JwtService 生成访问令牌和刷新令牌
        String accessToken = jwtTokenUtil.generateAccessToken(user.getId());
        String refreshToken = jwtTokenUtil.generateRefreshToken(user.getId());

        // 5. 保存 Refresh Token 到数据库
        // 可以先删除该用户之前所有的 Refresh Token，以实现单点登录
        refreshTokenService.remove(Wrappers.<RefreshToken>lambdaQuery()
                .eq(RefreshToken::getUserId, user.getId()));

        RefreshToken authRefreshToken = new RefreshToken();
        authRefreshToken.setId(MyUtils.getUUID());
        authRefreshToken.setUserId(user.getId());
        authRefreshToken.setRefreshToken(refreshToken);
        // 计算过期时间点 (Instant)
        Instant expirationInstant = Instant.now().plusMillis(jwtTokenUtil.getRefreshTokenExpiration());
        // 转换为 LocalDateTime (使用系统默认时区，可以指定时区，如 ZoneId.of("UTC"))
        LocalDateTime expiresLocalDateTime = expirationInstant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        authRefreshToken.setExpiresTime(expiresLocalDateTime);
        authRefreshToken.setCreateTime(LocalDateTime.now());
        refreshTokenService.save(authRefreshToken);

        // 6. 构建并返回 LoginResponse DTO
        return new LoginResponse(accessToken, refreshToken, jwtTokenUtil.getAccessTokenExpiration());
    }

    @Override
    @Transactional
    public LoginResponse refreshToken(String refreshToken) {
        // 1. 从数据库中查找并验证 Refresh Token
        RefreshToken storedToken = refreshTokenService.getOne(Wrappers.<RefreshToken>lambdaQuery()
                        .eq(RefreshToken::getRefreshToken, refreshToken));
        if (storedToken == null) {
            throw new RuntimeException(messageSource.getMessage("refresh-token.invalid", null, LocaleContextHolder.getLocale()));
        }

        // 2. 检查 Token 是否过期 (使用 LocalDateTime.now() 进行比较)
        if (storedToken.getExpiresTime().isBefore(LocalDateTime.now())) {
            refreshTokenService.remove(Wrappers.<RefreshToken>lambdaQuery()
                    .eq(RefreshToken::getRefreshToken, storedToken));
            throw new RuntimeException("refresh-token.expired");
        }

        // 3. 如果有效，加载用户信息并生成新的 Access Token
        User user = userService.getById(jwtTokenUtil.getUserIdFromToken(refreshToken));
        String newAccessToken = jwtTokenUtil.generateAccessToken(user.getId());

        return new LoginResponse(newAccessToken, refreshToken, jwtTokenUtil.getAccessTokenExpiration());
    }

    @Override
    @Transactional
    public void logout(String token) {
        String userId = jwtTokenUtil.getUserIdFromToken(token);
        refreshTokenService.remove(Wrappers.<RefreshToken>lambdaQuery()
                .eq(RefreshToken::getUserId, userId));
    }

    @Override
    public UserInfoDTO getUserInfo(String token) {
        String userId = jwtTokenUtil.getUserIdFromToken(token);
        User user = userService.getById(userId);
        UserInfoDTO infoDTO = new UserInfoDTO();
        infoDTO.setId(user.getId());
        infoDTO.setName(user.getAccount());
        infoDTO.setRoles(userService.getRoleCodeListByUserId(userId));
        return infoDTO;
    }
}
