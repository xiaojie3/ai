package com.example.demo.resources.service;

import com.example.demo.common.model.PageResult;
import com.example.demo.resources.model.dto.CampusDTO;
import com.example.demo.resources.model.dto.CampusQueryDTO;
import com.example.demo.resources.model.dto.CampusSaveDTO;

import java.util.List;

public interface CampusService {

    PageResult<CampusDTO> queryByPage(CampusQueryDTO queryDTO);

    List<CampusDTO> list();

    List<CampusDTO> list(CampusQueryDTO queryDTO);

    CampusDTO FindById(String id);

    void save(CampusSaveDTO saveDTO);

    void update(CampusSaveDTO saveDTO);

    void updateNotNll(CampusSaveDTO saveDTO);

    void deleteById(String id);

    void deleteByIds(List<String> ids);
}
