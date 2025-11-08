package com.example.ai.system.dto;

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
