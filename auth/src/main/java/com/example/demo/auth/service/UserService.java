package com.example.demo.auth.service;

import com.example.demo.auth.model.entity.User;

public interface UserService {
    User findByAccount(String account);

    User findById(String id);
}
