package com.example.demo.resources.service;

import com.example.demo.common.model.PageResult;
import com.example.demo.resources.model.dto.DepartmentBatchUpdateDTO;
import com.example.demo.resources.model.dto.DepartmentDTO;
import com.example.demo.resources.model.dto.DepartmentQueryDTO;
import com.example.demo.resources.model.dto.DepartmentSaveDTO;

import java.util.List;

public interface DepartmentService {

    PageResult<DepartmentDTO> queryByPage(DepartmentQueryDTO queryDTO);

    List<DepartmentDTO> list();

    List<DepartmentDTO> list(DepartmentQueryDTO queryDTO);

    DepartmentDTO FindById(String id);

    void save(DepartmentSaveDTO saveDTO);

    void update(DepartmentSaveDTO saveDTO);

    Integer batchUpdate(DepartmentBatchUpdateDTO batchUpdateDTO);

    void deleteById(String id);

    void deleteByIds(List<String> ids);
}
