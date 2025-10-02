package com.example.edu.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ai_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(unique = true, nullable = false)
    protected String account;  // 登录账号（唯一）

    @Column(nullable = false)
    protected String name;

    @Column(nullable = false)
    protected String password;  // 加密后的密码

    @Column(unique = true, nullable = false)
    protected String email;  // 邮箱（可选，用于找回密码）

    protected boolean enabled = true;  // 账号是否启用（默认启用）

    // 多对多关联：一个用户可拥有多个角色
    @ManyToMany(fetch = FetchType.EAGER)  // EAGER：加载用户时立即加载角色（认证时需要）
    @JoinTable(
            name = "ai_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    protected Set<RoleEntity> roles = new HashSet<>();
}
