package com.example.ai.auth.service;

import com.example.ai.auth.dto.LoginDto;

public interface AuthService {
    LoginDto login(String account, String password);
}
