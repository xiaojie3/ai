package com.example.ai.resources.service.impl;

import com.example.ai.common.model.PageResult;
import com.example.ai.common.util.MyUtils;
import com.example.ai.resources.model.dto.SchoolDTO;
import com.example.ai.resources.model.dto.SchoolQueryDTO;
import com.example.ai.resources.model.dto.SchoolSaveDTO;
import com.example.ai.resources.model.entity.School;
import com.example.ai.resources.repository.SchoolRepository;
import com.example.ai.resources.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SchoolServiceImpl implements SchoolService {
    private final SchoolRepository repository;

    @Override
    public PageResult<SchoolDTO> queryByPage(@RequestBody SchoolQueryDTO queryDTO) {
        return repository.queryByPage(queryDTO);
    }

    @Override
    public List<SchoolDTO> list() {
        return repository.list();
    }

    @Override
    public List<SchoolDTO> list(SchoolQueryDTO queryDTO) {
        return repository.list();
    }

    @Override
    public SchoolDTO FindById(String id) {
        return repository.FindById(id);
    }

    @Override
    public void save(SchoolSaveDTO saveDTO) {
        repository.save(MyUtils.copyObject(saveDTO, School.class));
    }

    @Override
    public void update(SchoolSaveDTO saveDTO) {
        repository.save(MyUtils.copyObject(saveDTO, School.class));
    }

    @Override
    public void updateNotNll(SchoolSaveDTO saveDTO) {
        repository.save(MyUtils.copyObjectNotNull(saveDTO, School.class));
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

