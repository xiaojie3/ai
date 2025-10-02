package com.example.edu.authservice.controller;

import com.example.edu.authservice.model.Course;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    // 任何拥有 read:course 权限的用户都可以访问
    @PreAuthorize("hasAuthority('read:course')")
    @GetMapping
    public List<Course> getAllCourses() {
        // ...
        return null;
    }

    // 只有拥有 create:course 权限的用户（即教师或管理员）可以访问
    @PreAuthorize("hasAuthority('create:course')")
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        // ...
        return ResponseEntity.ok(course);
    }
}
