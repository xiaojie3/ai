package com.example.demo.file.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 目录角色授权表
 *
 * @author Jerry
 * @since 2025-11-16 00:24:57
 */
@TableName("file_directory_role")
@Data
public class FileDirectoryRole {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    // 目录ID
    private String directoryId;

    // 角色编码（如ADMIN、USER、DEPT_READ）
    private String roleId;

    // 授权类型：1-读权限，2-读写权限
    private String authType;

    private LocalDateTime createTime;
}
