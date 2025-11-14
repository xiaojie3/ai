package com.example.demo.resources.service;

import com.example.demo.common.model.PageResult;
import com.example.demo.resources.model.dto.SchoolDTO;
import com.example.demo.resources.model.dto.SchoolQueryDTO;
import com.example.demo.resources.model.dto.SchoolSaveDTO;

import java.util.List;

public interface SchoolService {

    PageResult<SchoolDTO> queryByPage(SchoolQueryDTO queryDTO);

    List<SchoolDTO> list();

    List<SchoolDTO> list(SchoolQueryDTO queryDTO);

    SchoolDTO FindById(String id);

    void save(SchoolSaveDTO saveDTO);

    void update(SchoolSaveDTO saveDTO);

    void updateNotNll(SchoolSaveDTO saveDTO);

    void deleteById(String id);

    void deleteByIds(List<String> ids);
}
