package com.example.demo.resources.service;

import com.example.demo.common.model.PageResult;
import com.example.demo.resources.model.dto.CollegeBatchUpdateDTO;
import com.example.demo.resources.model.dto.CollegeDTO;
import com.example.demo.resources.model.dto.CollegeQueryDTO;
import com.example.demo.resources.model.dto.CollegeSaveDTO;

import java.util.List;

public interface CollegeService {

    PageResult<CollegeDTO> queryByPage(CollegeQueryDTO queryDTO);

    List<CollegeDTO> list();

    List<CollegeDTO> list(CollegeQueryDTO queryDTO);

    CollegeDTO FindById(String id);

    void save(CollegeSaveDTO saveDTO);

    void update(CollegeSaveDTO saveDTO);

    Integer batchUpdate(CollegeBatchUpdateDTO batchUpdateDTO);

    void deleteById(String id);

    void deleteByIds(List<String> ids);
}
