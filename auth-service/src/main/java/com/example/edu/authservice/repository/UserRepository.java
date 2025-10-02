package com.example.edu.authservice.repository;

import com.example.edu.authservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // Spring Data JPA 会自动根据方法名生成 SQL: SELECT * FROM users WHERE username = ?
    // 使用 Optional<User> 作为返回类型，以优雅地处理用户不存在的情况
    Optional<UserEntity> findByAccount(String account);
}