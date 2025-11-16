package com.example.demo.auth.service.impl;

import com.example.demo.auth.repository.RoleRepository;
import com.example.demo.auth.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<String> getRoleCodeListByUserId(String userId) {
        return roleRepository.getRoleCodeListByUserId(userId);
    }
}
