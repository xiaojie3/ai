package com.example.demo.auth.service;

import com.example.demo.auth.model.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(String account, String password);
    LoginResponse refreshToken(String refreshToken);

    void logout(String token);
}
