package com.example.demo.resources.repository;

import com.example.demo.common.model.PageResult;
import com.example.demo.resources.model.entity.Course;
import com.example.demo.resources.model.dto.CourseDTO;
import com.example.demo.resources.model.dto.CourseQueryDTO;

import java.util.List;

public interface CourseRepository {
    PageResult<CourseDTO> queryByPage(CourseQueryDTO queryDTO);

    List<CourseDTO> list();

    List<CourseDTO> list(CourseQueryDTO queryDTO);

    CourseDTO FindById(String id);

    Course getById(String id);

    void save(Course entity);

    Integer saveAll(List<Course> entities);

    void deleteById(String id);

    void deleteByIds(List<String> ids);
}
