package com.example.demo.file.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

/**
 * 目录角色授权表
 *
 * @author Jerry
 * @since 2025-11-16 00:24:57
 */
@Entity
@Table(name = "file_directory_role")
@Data
public class FileDirectoryRole {
    @Id
    @Column(length = 64)
    private String id;

    // 目录ID
    @Column(name = "directory_id")
    private String directoryId;

    // 角色编码（如ADMIN、USER、DEPT_READ）
    @Column(name = "role_id")
    private String roleId;

    // 授权类型：1-读权限，2-读写权限
    @Column(name = "auth_type")
    private String authType;

    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false) // 不可更新
    private LocalDateTime createTime;
}
