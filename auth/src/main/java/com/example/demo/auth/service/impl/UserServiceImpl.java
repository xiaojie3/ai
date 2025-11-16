package com.example.demo.auth.service.impl;

import com.example.demo.auth.model.entity.User;
import com.example.demo.auth.repository.UserRepository;
import com.example.demo.auth.service.RoleService;
import com.example.demo.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User findByAccount(String account) {
        return userRepository.findByAccount(account);
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id);
    }
}
