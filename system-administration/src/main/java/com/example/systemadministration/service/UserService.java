package com.example.systemadministration.service;

import com.example.edu.common.dto.UserDto;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> create(UserDto userDto);

    Optional<UserDto> findByAccount(String account);

    void deleteById(Long id);
}
