package com.example.ai.system.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @author makejava
 * @since 2025-11-08 23:46:49
 */
@Entity
@Table(name = "sys_user")
@Data
@EntityListeners(AuditingEntityListener.class) // 启用审计监听
public class SysUser {
    @Id
    @Column(length = 64)
    private String id;

    // 账号
    @Column(name = "account")
    private String account;

    // 名称
    @Column(name = "username")
    private String username;

    // 密码
    @Column(name = "password")
    private String password;

    // 邮箱
    @Column(name = "email")
    private String email;

    // 性别码
    @Column(name = "gender_code")
    private String genderCode;

    // 手机号码
    @Column(name = "phone")
    private String phone;

    // 状态码
    @Column(name = "user_status_code")
    private String userStatusCode;

    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false) // 不可更新
    private LocalDateTime createTime;

    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    @CreatedBy
    @Column(name = "create_by", length = 64, updatable = false)
    private String createBy;

    @LastModifiedBy
    @Column(name = "update_by", length = 64)
    private String updateBy;
}
