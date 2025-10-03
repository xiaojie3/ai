package com.example.edu.authservice.controller;

import com.example.edu.authservice.dto.LoginRequest;
import com.example.edu.authservice.dto.LoginResponse;
import com.example.edu.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth") // 统一的路由前缀
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login") // 登录接口
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}