package com.example.systemadministration.service;

import com.example.edu.common.dto.PermissionDto;
import com.example.edu.common.service.BaseService;

import java.util.Optional;

public interface PermissionService extends BaseService<PermissionDto>{

    Optional<PermissionDto> findByName(String name);
}
