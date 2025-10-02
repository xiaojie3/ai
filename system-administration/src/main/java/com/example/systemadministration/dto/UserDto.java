package com.example.systemadministration.dto;

import com.example.edu.common.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;


@Data
@EqualsAndHashCode(callSuper = true) // Lombok 注解，生成 equals 和 hashCode 时包含父类字段
public class UserDto extends BaseDto {

    private String username; // 登录用户名（唯一）

    private String email;  // 邮箱（可选，用于找回密码）

    private boolean enabled = true;

    private Set<RoleDto> roleDtoSets = new HashSet<>();
}
