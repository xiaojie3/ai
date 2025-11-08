package com.example.ai.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenUtil {
    // JWT 密钥（建议配置在 application.yml 中，长度至少 256 位）
    @Value("${jwt.secret:Quantum7Zebra32Falcon19Jade8Horizon47Vortex23Luna56Phoenix14Onyx78Tiger36Ruby29Eagle51Nebula84Fox17Dragon63Sapphire42Wolf91Iris37Hawk68Aurora25Bear74Emerald59Shark28Opal45Owl72Nova31Lion89Amethyst64Dolphin16Pearl53Raven87Orion49Panther35Topaz76Whale24Garnet61Sparrow93Aether58Leopard48Jasper82Falcon39Coral67Osprey21Vega73Stag52Vitrine94Hippo18Period75Raptor33Zodiac69}")
    private static String secret;

    // 令牌过期时间（72000 秒 = 20 小时）
    @Value("${jwt.expiration:72000000}")
    public static long expiration;

    // 生成 SecretKey（Spring Security 6 要求密钥必须是 SecretKey 类型）
    private static SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * 生成 JWT 令牌
     */
    public static String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                // 主题（用户名）
                .subject(userDetails.getUsername())
                // 签发时间
                .issuedAt(new Date())
                // 过期时间
                .expiration(new Date(System.currentTimeMillis() + expiration))
                // 签名算法 + 密钥
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .compact();
    }

    public static String generateRefreshToken(UserDetails userDetails) {
        return generateToken(userDetails);
    }

    /**
     * 从令牌中获取用户名
     */
    public static String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * 验证令牌有效性（用户名匹配 + 未过期）
     */
    public static boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // 解析令牌获取 Claims（负载）
    private static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // 检查令牌是否过期
    private static boolean isTokenExpired(String token) {
        return getClaimsFromToken(token).getExpiration().before(new Date());
    }
}
