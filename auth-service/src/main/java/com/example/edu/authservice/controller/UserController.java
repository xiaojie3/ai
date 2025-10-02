package com.example.edu.authservice.controller;

import com.example.edu.authservice.service.UserService;
import com.example.edu.common.model.UserContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user-permissions/{username}")
    public UserContext getUserPermissions(@PathVariable String username) {
        // 1. 从数据库或缓存中根据 username 查找用户
        // 2. 获取用户关联的角色
        // 3. 获取所有角色关联的权限
        // 4. 组装成 UserContext 对象返回
        return userService.getUserPermissions(username);
    }
}
