package com.example.demo.auth.controller;

import com.example.demo.auth.model.dto.LoginDto;
import com.example.demo.auth.model.dto.UserDetailsDto;
import com.example.demo.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth") // 统一的路由前缀
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login") // 登录接口
    public ResponseEntity<LoginDto> login(@RequestBody UserDetailsDto userDetailsDto) {
        return ResponseEntity.ok(authService.login(userDetailsDto.getUsername(), userDetailsDto.getPassword()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.refreshToken(loginDto.getRefreshToken()));
    }
}