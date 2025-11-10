package com.example.ai.system.dto;

import lombok.Data;

import java.util.List;

@Data
public class SysRoleSaveDTO {
    private String id;

    private List<String> ids;

    // ${column.comment}
    private String roleCode;

    // 角色名称
    private String roleName;

    // 角色描述
    private String description;

    // 是否启用
    private String enabled;
}
