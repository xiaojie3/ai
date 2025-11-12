package com.example.ai.resources.repository.impl;

import com.example.ai.common.model.BaseJooqRepository;
import com.example.ai.common.model.PageResult;
import com.example.ai.resources.model.dto.SchoolDTO;
import com.example.ai.resources.model.dto.SchoolQueryDTO;
import com.example.ai.resources.model.entity.School;
import com.example.ai.resources.repository.SchoolJpaRepository;
import com.example.ai.resources.repository.SchoolRepository;
import com.example.ai.resources.repository.jooq.Tables;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SchoolRepositoryImpl extends BaseJooqRepository implements SchoolRepository {
    private final SchoolJpaRepository jpaRepository;

    public SchoolRepositoryImpl(SchoolJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public PageResult<SchoolDTO> queryByPage(SchoolQueryDTO queryDTO) {
        return super.queryByPage(queryDTO, SchoolDTO.class);
    }

    @Override
    public List<SchoolDTO> list() {
        return super.list(SchoolDTO.class);
    }

    @Override
    public List<SchoolDTO> list(SchoolQueryDTO queryDTO) {
        return super.list(queryDTO, SchoolDTO.class);
    }

    @Override
    public SchoolDTO FindById(String id) {
        List<SchoolDTO> list = this.list(SchoolQueryDTO.builder().id(id).build());
        return list.isEmpty() ? null : list.getFirst();
    }

    @Override
    public void save(School entity) {
        jpaRepository.save(entity);
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
        return dsl.select().from(Tables.RES_SCHOOL);
    }

    @Override
    protected Table<?> mainTable() {
        return Tables.RES_SCHOOL;
    }
}

