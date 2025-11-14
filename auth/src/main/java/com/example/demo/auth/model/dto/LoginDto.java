package com.example.demo.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDto {

    private String accessToken;  // 访问令牌（用于访问受保护接口）

    private String refreshToken;  // 刷新令牌（用于获取新的访问令牌，可选）

    private String tokenType = "Bearer";  // 令牌类型（固定为 Bearer）

    private Long expiresIn;  // 访问令牌过期时间（单位：毫秒）
}
