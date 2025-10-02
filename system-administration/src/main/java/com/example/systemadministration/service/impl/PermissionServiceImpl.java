package com.example.systemadministration.service.impl;

import com.example.edu.common.dto.PermissionDto;
import com.example.edu.common.mapper.BaseMapper;
import com.example.edu.common.service.BaseService;
import com.example.edu.common.service.impl.BaseServiceImpl;
import com.example.systemadministration.entity.PermissionEntity;
import com.example.systemadministration.mapper.PermissionMapper;
import com.example.systemadministration.repository.PermissionRepository;
import com.example.systemadministration.service.PermissionService;
import com.example.systemadministration.vo.PermissionVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl extends BaseServiceImpl<PermissionEntity, PermissionDto, PermissionVo> implements PermissionService  {

    private final PermissionRepository repository;

    private final PermissionMapper mapper;

    @Override
    protected JpaRepository<PermissionEntity, Long> getRepository() {
        return repository;
    }

    @Override
    protected BaseMapper<PermissionEntity, PermissionDto, PermissionVo> getMapper() {
        return mapper;
    }

    @Override
    public Optional<PermissionDto> findByName(String name) {
        return Optional.of(mapper.toDto(repository.findByName(name)));
    }
}
