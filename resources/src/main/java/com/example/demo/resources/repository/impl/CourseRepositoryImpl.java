package com.example.demo.resources.repository.impl;

import com.example.demo.common.model.PageResult;
import com.example.demo.resources.model.dto.CourseDTO;
import com.example.demo.resources.model.dto.CourseQueryDTO;
import com.example.demo.resources.model.entity.Course;
import com.example.demo.resources.repository.jooq.Tables;
import com.example.demo.resources.repository.CourseJpaRepository;
import com.example.demo.resources.repository.CourseRepository;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseRepositoryImpl extends BaseJooqRepository implements CourseRepository {
    private final CourseJpaRepository jpaRepository;

    public CourseRepositoryImpl(CourseJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public PageResult<CourseDTO> queryByPage(CourseQueryDTO queryDTO) {
        return super.queryByPage(queryDTO, CourseDTO.class);
    }

    @Override
    public List<CourseDTO> list() {
        return super.list(CourseDTO.class);
    }

    @Override
    public List<CourseDTO> list(CourseQueryDTO queryDTO) {
        return super.list(queryDTO, CourseDTO.class);
    }

    @Override
    public CourseDTO FindById(String id) {
        List<CourseDTO> list = this.list(CourseQueryDTO.builder().id(id).build());
        return list.isEmpty() ? null : list.getFirst();
    }

    @Override
    public Course getById(String id) {
        return jpaRepository.getReferenceById(id);
    }

    @Override
    public void save(Course entity) {
        jpaRepository.save(entity);
    }

    @Override
    public Integer saveAll(List<Course> entities) {
        return jpaRepository.saveAll(entities).size();
    }

    @Override
    public void deleteById(String id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        jpaRepository.deleteAllById(ids);
    }

    @Override
    protected SelectJoinStep<?> createFrom(SelectSelectStep<?> sqlSelect) {
        return sqlSelect.from(Tables.RES_COURSE);
    }

    @Override
    protected Table<?> mainTable() {
        return Tables.RES_COURSE;
    }
}

