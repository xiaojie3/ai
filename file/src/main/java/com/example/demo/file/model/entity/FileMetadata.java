package com.example.demo.file.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

/**
 * 文件元数据表
 *
 * @author Jerry
 * @since 2025-11-16 00:24:57
 */
@Entity
@Table(name = "file_metadata")
@Data
public class FileMetadata {
    @Id
    @Column(length = 64)
    private String id;

    // 文件名
    @Column(name = "file_name")
    private String fileName;

    // 存储路径（本地/MinIO路径）
    @Column(name = "file_path")
    private String filePath;

    // 文件大小（字节）
    @Column(name = "file_size")
    private Long fileSize;

    // 文件类型（如image/png）
    @Column(name = "file_type")
    private String fileType;

    // 上传者ID（关联用户表）
    @Column(name = "uploader_id")
    private String uploaderId;

    // 所属目录ID
    @Column(name = "directory_id")
    private String directoryId;

    // 文件MD5（防重复上传）
    @Column(name = "md5")
    private String md5;

    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false) // 不可更新
    private LocalDateTime createTime;
}
