package com.example.ai.system.repository;

import com.example.ai.system.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleJpaRepository extends JpaRepository<SysRole, String> {
}
