package com.example.demo.resources.repository;

import com.example.demo.common.model.PageResult;
import com.example.demo.resources.model.entity.Campus;
import com.example.demo.resources.model.dto.CampusDTO;
import com.example.demo.resources.model.dto.CampusQueryDTO;

import java.util.List;

public interface CampusRepository {
    PageResult<CampusDTO> queryByPage(CampusQueryDTO queryDTO);

    List<CampusDTO> list();

    List<CampusDTO> list(CampusQueryDTO queryDTO);

    CampusDTO FindById(String id);

    Campus getById(String id);

    void save(Campus entity);

    void deleteById(String id);

    void deleteByIds(List<String> ids);
}
