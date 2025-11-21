package com.example.demo.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.auth.mapper.UserMapper;
import com.example.demo.auth.model.entity.User;
import com.example.demo.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserMapper userMapper;

    @Override
    public List<String> getRoleCodeListByUserId(String userId) {
        return userMapper.getRoleCodeListByUserId(userId);
    }
}
