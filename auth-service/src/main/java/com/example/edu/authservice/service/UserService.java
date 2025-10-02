package com.example.edu.authservice.service;

import com.example.edu.authservice.entity.User;
import com.example.edu.common.model.UserContext;

public interface UserService {
    UserContext getUserPermissions(String username);

    void createUser(User user);
}
