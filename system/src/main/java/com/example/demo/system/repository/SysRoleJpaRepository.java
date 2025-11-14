package com.example.demo.system.repository;

import com.example.demo.system.model.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleJpaRepository extends JpaRepository<SysRole, String> {
}
