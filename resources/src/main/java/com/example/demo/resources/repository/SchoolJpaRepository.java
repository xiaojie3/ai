package com.example.demo.resources.repository;

import com.example.demo.resources.model.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolJpaRepository extends JpaRepository<School, String> {
}
