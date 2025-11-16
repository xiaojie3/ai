package com.example.demo.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

public class JwtTokenUtil {
    private String secret = "Quantum7Zebra32Falcon19Jade8Horizon47Vortex23Luna56Phoenix14Onyx78Tiger36Ruby29Eagle51Nebula84Fox17Dragon63Sapphire42Wolf91Iris37Hawk68Aurora25Bear74Emerald59Shark28Opal45Owl72Nova31Lion89Amethyst64Dolphin16Pearl53Raven87Orion49Panther35Topaz76Whale24Garnet61Sparrow93Aether58Leopard48Jasper82Falcon39Coral67Osprey21Vega73Stag52Vitrine94Hippo18Period75Raptor33Zodiac69";
    @Getter
    private long accessTokenExpiration = 900000;
    @Getter
    private long refreshTokenExpiration = 604800000;

    public JwtTokenUtil() {}
    public JwtTokenUtil(String secret) {
        this.secret = secret;
    }

    public JwtTokenUtil(String secret, long accessTokenExpiration, long refreshTokenExpiration) {
        this.secret = secret;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }


    // 生成 SecretKey（Spring Security 6 要求密钥必须是 SecretKey 类型）
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成 Access Token
     */
    public String generateAccessToken(String userId) {
        return generateToken(userId, accessTokenExpiration);
    }

    /**
     * 生成 Refresh Token
     */
    public String generateRefreshToken(String userId) {
        return generateToken(userId, refreshTokenExpiration);
    }

    /**
     * 生成 Token 的通用方法
     */
    private String generateToken(String userId, long expiration) {
        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * 从令牌中获取用户名
     */
    public String getUserIdFromToken(String token) {;
        return getClaimsFromToken(token).getSubject();
    }

    // 解析令牌获取 Claims（负载）
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token.replace("Bearer ", ""))
                .getPayload();
    }

    /**
     * 验证令牌有效性（用户名匹配 + 未过期）
     */
    public boolean validateToken(String token, String userId) {
        String userIdFromToken = getUserIdFromToken(token);
        return userIdFromToken.equals(userId) && !isTokenExpired(token);
    }

    // 检查令牌是否过期
    private boolean isTokenExpired(String token) {
        Claims claims = getClaimsFromToken(token);
        // 1. 获取 JWT 中的过期时间（UTC，转为 Instant）
        Instant expirationInstant = claims.getExpiration().toInstant();
        // 2. 获取当前 UTC 时间（Instant.now() 直接返回 UTC 时间）
        Instant currentUtcInstant = Instant.now();
        // 3. 比较：如果过期时间在当前 UTC 时间之前，说明已过期
        return expirationInstant.isBefore(currentUtcInstant);
    }
}
