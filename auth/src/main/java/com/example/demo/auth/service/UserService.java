package com.example.demo.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.auth.model.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    List<String> getRoleCodeListByUserId(String userId);
}
