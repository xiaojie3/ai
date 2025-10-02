package com.example.systemadministration.service.impl;

import com.example.edu.common.dto.UserDto;
import com.example.edu.common.entity.BaseEntity;
import com.example.systemadministration.entity.RoleEntity;
import com.example.systemadministration.entity.UserEntity;
import com.example.systemadministration.mapper.UserMapper;
import com.example.systemadministration.repository.RoleRepository;
import com.example.systemadministration.repository.UserRepository;
import com.example.systemadministration.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public Optional<UserDto> create(UserDto userDto) {
        UserEntity user = userRepository.save(UserMapper.INSTANCE.toEntity(userDto));
        if("admin".equals(user.getAccount())) {
            RoleEntity role = roleRepository.findByName("admin");
            user.setRoles(Set.of(role));
        }
        return Optional.of(UserMapper.INSTANCE.toDto(user));
    }

    @Override
    public Optional<UserDto> findByAccount(String account) {
        UserEntity user = userRepository.findByAccount(account);
        return Optional.of(UserMapper.INSTANCE.toDto(user));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
