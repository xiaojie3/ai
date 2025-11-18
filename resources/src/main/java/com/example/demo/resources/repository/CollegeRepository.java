package com.example.demo.resources.repository;

import com.example.demo.common.model.PageResult;
import com.example.demo.resources.model.entity.College;
import com.example.demo.resources.model.dto.CollegeDTO;
import com.example.demo.resources.model.dto.CollegeQueryDTO;

import java.util.List;

public interface CollegeRepository {
    PageResult<CollegeDTO> queryByPage(CollegeQueryDTO queryDTO);

    List<CollegeDTO> list();

    List<CollegeDTO> list(CollegeQueryDTO queryDTO);

    CollegeDTO FindById(String id);

    College getById(String id);

    void save(College entity);

    Integer saveAll(List<College> entities);

    void deleteById(String id);

    void deleteByIds(List<String> ids);
}
