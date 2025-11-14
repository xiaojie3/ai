package com.example.demo.auth.repository;

import com.example.demo.auth.model.dto.UserDetailsDto;

public interface UserRepository {
    UserDetailsDto findByAccount(String account);
}
