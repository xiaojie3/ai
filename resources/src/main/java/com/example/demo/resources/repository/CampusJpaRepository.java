package com.example.demo.resources.repository;

import com.example.demo.resources.model.entity.Campus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampusJpaRepository extends JpaRepository<Campus, String> {
}
