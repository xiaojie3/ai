package com.example.demo.resources.service.impl;

import com.example.demo.common.context.UserContextHolder;
import com.example.demo.common.model.PageResult;
import com.example.demo.common.util.MyUtils;
import com.example.demo.resources.model.dto.CollegeBatchUpdateDTO;
import com.example.demo.resources.model.dto.CollegeDTO;
import com.example.demo.resources.model.dto.CollegeQueryDTO;
import com.example.demo.resources.model.dto.CollegeSaveDTO;
import com.example.demo.resources.model.entity.College;
import com.example.demo.resources.repository.CollegeRepository;
import com.example.demo.resources.service.CollegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CollegeServiceImpl implements CollegeService {
    private final CollegeRepository repository;

    @Override
    public PageResult<CollegeDTO> queryByPage(@RequestBody CollegeQueryDTO queryDTO) {
        return repository.queryByPage(queryDTO);
    }

    @Override
    public List<CollegeDTO> list() {
        return repository.list();
    }

    @Override
    public List<CollegeDTO> list(CollegeQueryDTO queryDTO) {
        return repository.list();
    }

    @Override
    public CollegeDTO FindById(String id) {
        return repository.FindById(id);
    }

    @Override
    public void save(CollegeSaveDTO saveDTO) {
        College entity = MyUtils.copyObject(saveDTO, College.class);
        entity.setId(MyUtils.getUUID());
        entity.setCreateBy(UserContextHolder.getUserId());
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    public void update(CollegeSaveDTO saveDTO) {
        College entity = MyUtils.copyObject(saveDTO, College.class);
        entity.setUpdateBy(UserContextHolder.getUserId());
        entity.setUpdateTime(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    public Integer batchUpdate(CollegeBatchUpdateDTO batchUpdateDTO) {
        List <College> entityList = new ArrayList<>();
        batchUpdateDTO.getIds().forEach(id -> {
            College entity = repository.getById(id);
            MyUtils.copyObject(batchUpdateDTO, entity, false);
            entity.setUpdateBy(UserContextHolder.getUserId());
            entity.setUpdateTime(LocalDateTime.now());
            entityList.add(entity);
        });
        return repository.saveAll(entityList);
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

