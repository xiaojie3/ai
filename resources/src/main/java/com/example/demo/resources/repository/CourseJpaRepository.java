package com.example.demo.resources.repository;

import com.example.demo.resources.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseJpaRepository extends JpaRepository<Course, String> {
}
