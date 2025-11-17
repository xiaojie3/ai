package com.example.demo.resources.repository.impl;

import com.example.demo.common.model.BaseJooqRepository;
import com.example.demo.common.model.PageResult;
import com.example.demo.resources.model.dto.SchoolDTO;
import com.example.demo.resources.model.dto.SchoolQueryDTO;
import com.example.demo.resources.model.entity.School;
import com.example.demo.resources.repository.SchoolJpaRepository;
import com.example.demo.resources.repository.SchoolRepository;
import com.example.demo.resources.repository.jooq.Tables;
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

    @Override    // 标注此方法重写了父类的方法
    public SchoolDTO FindById(String id) {    // 根据ID查找学校信息并返回SchoolDTO对象
        // 使用构建器模式创建查询条件，根据传入的id进行查询
        List<SchoolDTO> list = this.list(SchoolQueryDTO.builder().id(id).build());
        // 判断查询结果列表是否为空，若为空则返回null，否则返回列表中的第一个元素
        return list.isEmpty() ? null : list.getFirst();
    }

    @Override
    public School FindByIdEntity(String id) {
        return jpaRepository.getReferenceById(id);
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

