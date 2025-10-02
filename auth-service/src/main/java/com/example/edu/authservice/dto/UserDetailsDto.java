package com.example.edu.authservice.dto;

import com.example.edu.authservice.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetailsDto implements UserDetails {

    private final UserEntity user;

    public UserDetailsDto(UserEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 将用户的角色转换为 Spring Security 所需的 GrantedAuthority
        return user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getAccount();
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

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}