package com.example.demo.auth.service;

import com.example.demo.auth.model.dto.LoginResponse;
import com.example.demo.auth.model.dto.UserInfoDTO;

public interface AuthService {
    LoginResponse login(String account, String password);

    LoginResponse refreshToken(String refreshToken);

    void logout(String token);

    UserInfoDTO getUserInfo(String token);
}
