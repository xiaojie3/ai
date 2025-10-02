package com.example.systemadministration.service;

import com.example.edu.common.service.BaseService;
import com.example.systemadministration.dto.PermissionDto;
import com.example.systemadministration.entity.PermissionEntity;

import java.util.Optional;

public interface PermissionService extends BaseService<PermissionEntity,PermissionDto> {

    Optional<PermissionDto> findByName(String name);
}
