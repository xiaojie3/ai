package com.example.systemadministration.repository;

import com.example.systemadministration.entity.PermissionEntity;
import com.example.systemadministration.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
    PermissionEntity findByName(String name);
}
