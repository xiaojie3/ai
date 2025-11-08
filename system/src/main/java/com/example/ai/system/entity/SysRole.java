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
 * 角色表
 *
 * @author makejava
 * @since 2025-11-09 00:35:31
 */
@Entity
@Table(name = "sys_role")
@Data
@EntityListeners(AuditingEntityListener.class) // 启用审计监听
public class SysRole {
    @Id
    @Column(length = 64)
    private String id;

    // ${column.comment}
    @Column(name = "role_code")
    private String roleCode;

    // 角色名称
    @Column(name = "role_name")
    private String roleName;

    // 角色描述
    @Column(name = "description")
    private String description;

    // 是否启用
    @Column(name = "enabled")
    private String enabled;

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
