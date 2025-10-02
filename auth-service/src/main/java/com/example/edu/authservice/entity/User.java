package com.example.edu.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "ai_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {  // 实现 UserDetails，适配 Spring Security

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;  // 登录用户名（唯一）

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
    private Set<Role> roles = new HashSet<>();

    // ------------------------------
    // 实现 UserDetails 接口的方法
    // ------------------------------
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 将用户的角色转换为 Spring Security 所需的 GrantedAuthority
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // 简化：账号永不过期（实际项目可加过期逻辑）
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // 简化：账号永不锁定（实际项目可加锁定逻辑）
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // 简化：密码永不过期（实际项目可加密码过期逻辑）
    }
}
