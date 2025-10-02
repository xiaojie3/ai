package com.example.systemadministration.service.impl;

import com.example.edu.common.MyUtils;
import com.example.edu.common.service.impl.BaseServiceImpl;
import com.example.systemadministration.dto.PermissionDto;
import com.example.systemadministration.entity.PermissionEntity;
import com.example.systemadministration.repository.PermissionRepository;
import com.example.systemadministration.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl extends BaseServiceImpl<PermissionEntity,PermissionDto> implements PermissionService  {

    private final PermissionRepository repository;

    @Override
    public Optional<PermissionDto> findByName(String name) {
        return Optional.of(toDto(repository.findByName(name)));
    }

    @Override
    protected JpaRepository<PermissionEntity, Long> getRepository() {
        return repository;
    }


    @Override
    public PermissionDto toDto(PermissionEntity entity) {
        PermissionDto dto = new PermissionDto();
        MyUtils.copyProperties(entity,dto);
        return dto;
    }

    @Override
    public PermissionEntity toEntity(PermissionDto dto) {
        PermissionEntity entity = new PermissionEntity();
        MyUtils.copyProperties(dto,entity);
        return entity;
    }
}
