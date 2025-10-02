package com.example.systemadministration.service.impl;

import com.example.edu.common.MyUtils;
import com.example.systemadministration.dto.UserDto;
import com.example.systemadministration.entity.RoleEntity;
import com.example.systemadministration.entity.UserEntity;
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
        UserEntity user = userRepository.save(MyUtils.copyProperties(userDto, UserEntity.class));
        if("admin".equals(user.getAccount())) {
            RoleEntity role = roleRepository.findByName("admin");
            user.setRoles(Set.of(role));
        }
        return Optional.of(MyUtils.copyProperties(user, UserDto.class));
    }

    @Override
    public Optional<UserDto> findByAccount(String account) {
        UserEntity user = userRepository.findByAccount(account);
        return Optional.of(MyUtils.copyProperties(user, UserDto.class));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
