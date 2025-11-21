package com.example.demo.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色表
 *
 * @author xieJ
 * @since 2025-11-09 00:35:31
 */
@Data
@TableName("`sys_role`")
public class Role {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String code;

    // 角色名称
    private String name;

    private String groupId;

    // 角色描述
    private String description;

    // 是否启用
    private String enabled;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String createBy;

    private String updateBy;
}
