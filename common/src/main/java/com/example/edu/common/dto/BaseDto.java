package com.example.edu.common.dto;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.util.Date;

@Data // 使用 Lombok 简化 getter/setter
@MappedSuperclass // 关键注解：表示这是一个映射的超类，其属性将被继承
public abstract class BaseDto {
    private Long id;

    protected Long creator;

    protected Date createDate;

    protected Long modifier;

    protected Date modifyDate;
}
