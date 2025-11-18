package com.example.demo.resources.repository;

import com.example.demo.resources.model.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeJpaRepository extends JpaRepository<College, String> {
}
