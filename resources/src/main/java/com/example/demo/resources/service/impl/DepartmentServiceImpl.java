package com.example.demo.resources.service.impl;

import com.example.demo.common.context.UserContextHolder;
import com.example.demo.common.model.PageResult;
import com.example.demo.common.util.MyUtils;
import com.example.demo.resources.model.dto.DepartmentBatchUpdateDTO;
import com.example.demo.resources.model.dto.DepartmentDTO;
import com.example.demo.resources.model.dto.DepartmentQueryDTO;
import com.example.demo.resources.model.dto.DepartmentSaveDTO;
import com.example.demo.resources.model.entity.Department;
import com.example.demo.resources.repository.DepartmentRepository;
import com.example.demo.resources.service.DepartmentService;
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
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository repository;

    @Override
    public PageResult<DepartmentDTO> queryByPage(@RequestBody DepartmentQueryDTO queryDTO) {
        return repository.queryByPage(queryDTO);
    }

    @Override
    public List<DepartmentDTO> list() {
        return repository.list();
    }

    @Override
    public List<DepartmentDTO> list(DepartmentQueryDTO queryDTO) {
        return repository.list();
    }

    @Override
    public DepartmentDTO FindById(String id) {
        return repository.FindById(id);
    }

    @Override
    public void save(DepartmentSaveDTO saveDTO) {
        saveDTO.setId(MyUtils.getUUID());
        Department entity = MyUtils.copyObject(saveDTO, Department.class);
        entity.setCreateBy(UserContextHolder.getUserId());
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    public void update(DepartmentSaveDTO saveDTO) {
        Department entity = MyUtils.copyObject(saveDTO, Department.class);
        entity.setUpdateBy(UserContextHolder.getUserId());
        entity.setUpdateTime(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    public Integer batchUpdate(DepartmentBatchUpdateDTO batchUpdateDTO) {
        List<Department> entityList = new ArrayList<>();
        batchUpdateDTO.getIds().forEach(id -> {
            Department entity = repository.getById(id);
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

