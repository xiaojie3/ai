package com.example.demo.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class JwtTokenUtil {
    private final static String SECRET = "Quantum7Zebra32Falcon19Jade8Horizon47Vortex23Luna56Phoenix14Onyx78Tiger36Ruby29Eagle51Nebula84Fox17Dragon63Sapphire42Wolf91Iris37Hawk68Aurora25Bear74Emerald59Shark28Opal45Owl72Nova31Lion89Amethyst64Dolphin16Pearl53Raven87Orion49Panther35Topaz76Whale24Garnet61Sparrow93Aether58Leopard48Jasper82Falcon39Coral67Osprey21Vega73Stag52Vitrine94Hippo18Period75Raptor33Zodiac69";

    // 生成 SecretKey（Spring Security 6 要求密钥必须是 SecretKey 类型）
    private static SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 从令牌中获取用户名
     */
    public static String getAccountFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    // 解析令牌获取 Claims（负载）
    private static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
