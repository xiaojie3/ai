package com.example.demo.resources.service.impl;

import com.example.demo.common.context.UserContextHolder;
import com.example.demo.common.model.PageResult;
import com.example.demo.common.util.MyUtils;
import com.example.demo.resources.model.dto.CourseBatchUpdateDTO;
import com.example.demo.resources.model.dto.CourseDTO;
import com.example.demo.resources.model.dto.CourseQueryDTO;
import com.example.demo.resources.model.dto.CourseSaveDTO;
import com.example.demo.resources.model.entity.Course;
import com.example.demo.resources.repository.CourseRepository;
import com.example.demo.resources.service.CourseService;
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
public class CourseServiceImpl implements CourseService {
    private final CourseRepository repository;

    @Override
    public PageResult<CourseDTO> queryByPage(@RequestBody CourseQueryDTO queryDTO) {
        return repository.queryByPage(queryDTO);
    }

    @Override
    public List<CourseDTO> list() {
        return repository.list();
    }

    @Override
    public List<CourseDTO> list(CourseQueryDTO queryDTO) {
        return repository.list();
    }

    @Override
    public CourseDTO FindById(String id) {
        return repository.FindById(id);
    }

    @Override
    public void save(CourseSaveDTO saveDTO) {
        Course entity = MyUtils.copyObject(saveDTO, Course.class);
        entity.setId(MyUtils.getUUID());
        entity.setCreateBy(UserContextHolder.getUserId());
        entity.setCreateTime(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    public void update(CourseSaveDTO saveDTO) {
        Course entity = MyUtils.copyObject(saveDTO, Course.class);
        entity.setUpdateBy(UserContextHolder.getUserId());
        entity.setUpdateTime(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    public Integer batchUpdate(CourseBatchUpdateDTO batchUpdateDTO) {
        List<Course> entityList = new ArrayList<>();
        batchUpdateDTO.getIds().forEach(id -> {
            Course entity = repository.getById(id);
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

