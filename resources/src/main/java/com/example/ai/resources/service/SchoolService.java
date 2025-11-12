package com.example.ai.resources.service;

import com.example.ai.common.model.PageResult;
import com.example.ai.resources.model.dto.SchoolDTO;
import com.example.ai.resources.model.dto.SchoolQueryDTO;
import com.example.ai.resources.model.dto.SchoolSaveDTO;

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
