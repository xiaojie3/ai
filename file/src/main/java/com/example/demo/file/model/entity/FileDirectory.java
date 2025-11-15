package com.example.demo.file.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

/**
 * 文件目录表
 *
 * @author Jerry
 * @since 2025-11-16 00:24:57
 */
@Entity
@Table(name = "file_directory")
@Data
public class FileDirectory {
    @Id
    @Column(length = 64)
    private String id;

    // 目录名称
    @Column(name = "directory_name")
    private String directoryName;

    // 创建者ID
    @Column(name = "creator_id")
    private String creatorId;

    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false) // 不可更新
    private LocalDateTime createTime;
}
