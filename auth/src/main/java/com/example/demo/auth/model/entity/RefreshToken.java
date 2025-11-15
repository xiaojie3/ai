package com.example.demo.auth.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "auth_refresh_token")
public class RefreshToken {
    @Id
    private String id;
    private String account;
    private String refreshToken;
    private LocalDateTime expiresTime;
    private LocalDateTime createTime;
}
