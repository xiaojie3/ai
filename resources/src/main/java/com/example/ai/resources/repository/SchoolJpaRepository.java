package com.example.ai.resources.repository;

import com.example.ai.resources.model.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolJpaRepository extends JpaRepository<School, String> {
}
