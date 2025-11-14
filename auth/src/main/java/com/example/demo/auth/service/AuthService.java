package com.example.demo.auth.service;

import com.example.demo.auth.model.dto.LoginDto;

public interface AuthService {
    LoginDto login(String account, String password);
    LoginDto refreshToken(String refreshToken);
}
