package com.example.demo.resources.repository.impl;

import com.example.demo.common.model.PageResult;
import com.example.demo.resources.model.dto.CollegeDTO;
import com.example.demo.resources.model.dto.CollegeQueryDTO;
import com.example.demo.resources.model.entity.College;
import com.example.demo.resources.repository.CollegeJpaRepository;
import com.example.demo.resources.repository.CollegeRepository;
import com.example.demo.resources.repository.jooq.Tables;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CollegeRepositoryImpl extends BaseJooqRepository implements CollegeRepository {
    private final CollegeJpaRepository jpaRepository;

    public CollegeRepositoryImpl(CollegeJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public PageResult<CollegeDTO> queryByPage(CollegeQueryDTO queryDTO) {
        return super.queryByPage(queryDTO, CollegeDTO.class);
    }

    @Override
    public List<CollegeDTO> list() {
        return super.list(CollegeDTO.class);
    }

    @Override
    public List<CollegeDTO> list(CollegeQueryDTO queryDTO) {
        return super.list(queryDTO, CollegeDTO.class);
    }

    @Override
    public CollegeDTO FindById(String id) {
        List<CollegeDTO> list = this.list(CollegeQueryDTO.builder().id(id).build());
        return list.isEmpty() ? null : list.getFirst();
    }

    @Override
    public College getById(String id) {
        return jpaRepository.getReferenceById(id);
    }

    @Override
    public void save(College entity) {
        jpaRepository.save(entity);
    }

    @Override
    public Integer saveAll(List<College> entities) {
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
        return sqlSelect.from(Tables.RES_COLLEGE);
    }

    @Override
    protected Table<?> mainTable() {
        return Tables.RES_COLLEGE;
    }
}

