package com.example.demo.resources.repository;

import com.example.demo.common.model.PageResult;
import com.example.demo.resources.model.entity.School;
import com.example.demo.resources.model.dto.SchoolDTO;
import com.example.demo.resources.model.dto.SchoolQueryDTO;

import java.util.List;

public interface SchoolRepository {
    PageResult<SchoolDTO> queryByPage(SchoolQueryDTO queryDTO);

    List<SchoolDTO> list();

    List<SchoolDTO> list(SchoolQueryDTO queryDTO);

    SchoolDTO FindById(String id);

    void save(School entity);

    void deleteById(String id);

    void deleteByIds(List<String> ids);
}
