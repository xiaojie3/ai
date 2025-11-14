package com.example.demo.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenUtil {

    @Getter
    @Value("${jwt.access-token.expiration}")
    private long accessTokenExpiration;

    @Getter
    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    @Value("${jwt.secret}")
    private String secret;

    // 生成 SecretKey（Spring Security 6 要求密钥必须是 SecretKey 类型）
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成 Access Token
     */
    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails, accessTokenExpiration);
    }

    /**
     * 生成 Refresh Token
     */
    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(userDetails, refreshTokenExpiration);
    }

    /**
     * 生成 Token 的通用方法
     */
    private String generateToken(UserDetails userDetails, long expiration) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * 从令牌中获取用户名
     */
    public String getAccountFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * 验证令牌有效性（用户名匹配 + 未过期）
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String account = getAccountFromToken(token);
        return account.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // 解析令牌获取 Claims（负载）
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // 检查令牌是否过期
    private boolean isTokenExpired(String token) {
        return getClaimsFromToken(token).getExpiration().before(new Date());
    }
}
