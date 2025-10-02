package com.example.systemadministration.entity;

import com.example.edu.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(name = "ai_permissions")
@Data
@EqualsAndHashCode(callSuper = true) // Lombok 注解，生成 equals 和 hashCode 时包含父类字段
@Entity
public class PermissionEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;  // 权限名（如：read:course, create:assignment, manage:user）
}
