package com.example.demo.resources.service;

import com.example.demo.common.model.PageResult;
import com.example.demo.resources.model.dto.CourseBatchUpdateDTO;
import com.example.demo.resources.model.dto.CourseDTO;
import com.example.demo.resources.model.dto.CourseQueryDTO;
import com.example.demo.resources.model.dto.CourseSaveDTO;

import java.util.List;

public interface CourseService {

    PageResult<CourseDTO> queryByPage(CourseQueryDTO queryDTO);

    List<CourseDTO> list();

    List<CourseDTO> list(CourseQueryDTO queryDTO);

    CourseDTO FindById(String id);

    void save(CourseSaveDTO saveDTO);

    void update(CourseSaveDTO saveDTO);

    Integer batchUpdate(CourseBatchUpdateDTO batchUpdateDTO);

    void deleteById(String id);

    void deleteByIds(List<String> ids);
}
