package com.example.ai.system.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class SysRoleSaveDTO {
    private String id;

    // ${column.comment}
    private String roleCode;

    // 角色名称
    private String roleName;

    // 角色描述
    private String description;

    // 是否启用
    private String enabled;
}
