package com.example.edu.authservice.service.impl;

import com.example.edu.authservice.entity.User;
import com.example.edu.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // 将该类注册为 Spring Bean
@RequiredArgsConstructor // 使用 Lombok 自动注入依赖
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional // 确保在事务中加载用户及其关联的角色/权限
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 从数据库中根据用户名查询用户
        // 使用 orElseThrow() 来处理用户不存在的情况，如果找不到则抛出 UsernameNotFoundException
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        // 2. 返回 UserDetails 对象
        // 我们的 User 实体类已经实现了 UserDetails 接口，所以可以直接返回
        // Spring Security 会使用这个返回的 UserDetails 对象来进行后续的认证（密码比对等）
        return user;
    }
}
