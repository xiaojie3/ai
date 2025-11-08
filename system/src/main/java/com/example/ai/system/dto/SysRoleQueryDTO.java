package com.example.ai.system.dto;

import java.time.LocalDateTime;

import com.example.ai.common.model.PageParams;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class SysRoleQueryDTO extends PageParams {
    private String id;

    // ${column.comment}
    private String roleCode;

    // 角色名称
    private String roleName;

    // 角色描述
    private String description;

    // 是否启用
    private String enabled;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private String createBy;

    private String updateBy;
}
