package com.example.systemadministration.service;

import com.example.systemadministration.dto.UserDto;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> create(UserDto userDto);

    Optional<UserDto> findByAccount(String account);

    void deleteById(Long id);
}
