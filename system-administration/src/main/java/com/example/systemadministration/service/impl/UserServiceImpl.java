package com.example.systemadministration.service.impl;

import com.example.edu.common.MyUtils;
import com.example.systemadministration.dto.UserDto;
import com.example.systemadministration.entity.RoleEntity;
import com.example.systemadministration.entity.UserEntity;
import com.example.systemadministration.repository.RoleRepository;
import com.example.systemadministration.repository.UserRepository;
import com.example.systemadministration.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder; // 注入 PasswordEncoder

    @Override
    public Optional<UserDto> create(UserDto userDto) {
        UserEntity user = MyUtils.copyProperties(userDto, UserEntity.class);
        if("admin".equals(user.getAccount())) {
            RoleEntity role = roleRepository.findByName("admin");
            user.setRoles(Set.of(role));
        }
        user.setPassword(passwordEncoder.encode("admin123"));
        return Optional.of(MyUtils.copyProperties(userRepository.save(user), UserDto.class));
    }

    @Override
    public Optional<UserDto> findByAccount(String account) {
        return userRepository.findByAccount(account)
                .map(user -> MyUtils.copyProperties(user, UserDto.class));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
