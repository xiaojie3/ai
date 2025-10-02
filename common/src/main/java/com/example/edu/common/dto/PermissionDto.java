package com.example.edu.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true) // Lombok 注解，生成 equals 和 hashCode 时包含父类字段
public class PermissionDto extends BaseDto{

    private String name;
}
