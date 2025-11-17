package com.example.demo.resources.repository;

import com.example.demo.common.model.PageResult;
import com.example.demo.resources.model.entity.Department;
import com.example.demo.resources.model.dto.DepartmentDTO;
import com.example.demo.resources.model.dto.DepartmentQueryDTO;

import java.util.List;

public interface DepartmentRepository {
    PageResult<DepartmentDTO> queryByPage(DepartmentQueryDTO queryDTO);

    List<DepartmentDTO> list();

    List<DepartmentDTO> list(DepartmentQueryDTO queryDTO);

    DepartmentDTO FindById(String id);

    Department getById(String id);

    void save(Department entity);

    Integer saveAll(List<Department> entities);

    void deleteById(String id);

    void deleteByIds(List<String> ids);
}
