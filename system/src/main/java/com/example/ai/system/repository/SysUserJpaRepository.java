package com.example.ai.system.repository;

import com.example.ai.common.model.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserJpaRepository extends JpaRepository<SysUser, String> {
}
