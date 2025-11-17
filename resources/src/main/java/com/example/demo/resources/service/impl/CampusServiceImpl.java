package com.example.demo.resources.service.impl;

import com.example.demo.common.context.UserContextHolder;
import com.example.demo.common.model.PageResult;
import com.example.demo.common.util.MyUtils;
import com.example.demo.resources.model.dto.CampusDTO;
import com.example.demo.resources.model.dto.CampusQueryDTO;
import com.example.demo.resources.model.dto.CampusSaveDTO;
import com.example.demo.resources.model.entity.Campus;
import com.example.demo.resources.repository.CampusRepository;
import com.example.demo.resources.service.CampusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CampusServiceImpl implements CampusService {
    private final CampusRepository repository;

    @Override
    public PageResult<CampusDTO> queryByPage(@RequestBody CampusQueryDTO queryDTO) {
        return repository.queryByPage(queryDTO);
    }

    @Override
    public List<CampusDTO> list() {
        return repository.list();
    }

    @Override
    public List<CampusDTO> list(CampusQueryDTO queryDTO) {
        return repository.list();
    }

    @Override
    public CampusDTO FindById(String id) {
        return repository.FindById(id);
    }

    @Override
    public void save(CampusSaveDTO saveDTO) {
        Campus entity = MyUtils.copyObject(saveDTO, Campus.class);
        entity.setId(MyUtils.getUUID());
        entity.setCreateBy(UserContextHolder.getUserId());
        entity.setCreateTime(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    public void update(CampusSaveDTO saveDTO) {
        Campus entity = MyUtils.copyObject(saveDTO, Campus.class);
        entity.setUpdateBy(UserContextHolder.getUserId());
        entity.setUpdateTime(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    public void updateNotNll(CampusSaveDTO saveDTO) {
        Campus entity = repository.getById(saveDTO.getId());
        MyUtils.copyObject(saveDTO, entity, false);
        entity.setUpdateBy(UserContextHolder.getUserId());
        entity.setUpdateTime(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        repository.deleteByIds(ids);
    }
}

