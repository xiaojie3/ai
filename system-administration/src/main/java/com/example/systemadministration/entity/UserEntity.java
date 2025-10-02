package com.example.systemadministration.entity;

import com.example.edu.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Table(name = "ai_users")
@Data
@EqualsAndHashCode(callSuper = true) // Lombok 注解，生成 equals 和 hashCode 时包含父类字段
@Entity
public class UserEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String account;  // 登录账号（唯一）

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;  // 加密后的密码

    @Column(unique = true, nullable = false)
    private String email;  // 邮箱（可选，用于找回密码）

    private boolean enabled = true;  // 账号是否启用（默认启用）

    // 多对多关联：一个用户可拥有多个角色
    @ManyToMany(fetch = FetchType.EAGER)  // EAGER：加载用户时立即加载角色（认证时需要）
    @JoinTable(
            name = "ai_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();
}
