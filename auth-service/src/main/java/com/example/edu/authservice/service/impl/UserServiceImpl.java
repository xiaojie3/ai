package com.example.edu.authservice.service.impl;

import com.example.edu.authservice.entity.User;
import com.example.edu.authservice.repository.UserRepository;
import com.example.edu.authservice.service.UserService;
import com.example.edu.common.model.UserContext;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserContext getUserPermissions(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // 构建并返回 UserContext 对象
            return new UserContext(
                    user.getId().toString(), // 假设 userId 是 Long 类型
                    user.getUsername(),
                    user.getAuthorities().stream()
                            .map(authority -> authority.getAuthority())
                            .collect(Collectors.toSet())
            );
        }
        // 用户不存在，返回 null 或抛出异常，由调用方处理
        return null;
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }
}
