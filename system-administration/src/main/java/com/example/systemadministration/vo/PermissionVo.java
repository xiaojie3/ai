package com.example.systemadministration.vo;


import com.example.edu.common.vo.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true) // Lombok 注解，生成 equals 和 hashCode 时包含父类字段
public class PermissionVo extends BaseVo {

    private String name;
}
