package com.example.edu.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data // 使用 Lombok 简化 getter/setter
@MappedSuperclass // 关键注解：表示这是一个映射的超类，其属性将被继承
@EntityListeners(AuditingEntityListener.class) // 关键注解：启用 JPA 的审计功能
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreatedBy // 自动填充创建者
    @Column(updatable = false) // 创建后不允许修改
    protected Long creator;

    @CreatedDate // 自动填充创建时间
    @Column(updatable = false)
    protected Date createDate;

    @LastModifiedBy // 自动填充修改者
    protected Long modifier;

    @LastModifiedDate // 自动填充修改时间
    protected Date modifyDate;
}
