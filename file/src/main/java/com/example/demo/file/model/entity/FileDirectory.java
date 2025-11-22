package com.example.demo.file.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件目录表
 *
 * @author Jerry
 * @since 2025-11-16 00:24:57
 */
@TableName("file_directory")
@Data
public class FileDirectory {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    // 目录名称
    private String directoryName;

    // 创建者ID
    private String creatorId;

    private LocalDateTime createTime;
}
