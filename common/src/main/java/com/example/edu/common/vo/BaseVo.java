package com.example.edu.common.vo;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data // 使用 Lombok 简化 getter/setter
@MappedSuperclass // 关键注解：表示这是一个映射的超类，其属性将被继承
public abstract class BaseVo {

    protected Long id;
}
