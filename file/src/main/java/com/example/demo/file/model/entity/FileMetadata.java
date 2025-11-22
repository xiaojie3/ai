package com.example.demo.file.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件元数据表
 *
 * @author Jerry
 * @since 2025-11-16 00:24:57
 */
@TableName("file_metadata")
@Data
public class FileMetadata {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    // 文件名
    private String fileName;

    // 存储路径（本地/MinIO路径）
    private String filePath;

    // 文件大小（字节）
    private Long fileSize;

    // 文件类型（如image/png）
    private String fileType;

    // 上传者ID（关联用户表）
    private String uploaderId;

    // 所属目录ID
    private String directoryId;

    // 文件MD5（防重复上传）
    private String md5;

    private LocalDateTime createTime;
}
