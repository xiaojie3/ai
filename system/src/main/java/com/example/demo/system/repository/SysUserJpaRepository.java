package com.example.demo.system.repository;

import com.example.demo.system.model.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserJpaRepository extends JpaRepository<SysUser, String> {
}
