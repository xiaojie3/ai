package com.example.demo.auth.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("auth_refresh_token")
public class RefreshToken {
    private String id;
    private String userId;
    private String refreshToken;
    private LocalDateTime expiresTime;
    private LocalDateTime createTime;
}
