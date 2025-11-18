package com.example.demo.resources.repository.impl;

import com.example.demo.common.model.BaseJooqRepository;
import com.example.demo.common.model.PageResult;
import com.example.demo.resources.model.dto.DepartmentDTO;
import com.example.demo.resources.model.dto.DepartmentQueryDTO;
import com.example.demo.resources.model.entity.Department;
import com.example.demo.resources.repository.jooq.Tables;
import com.example.demo.resources.repository.DepartmentJpaRepository;
import com.example.demo.resources.repository.DepartmentRepository;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentRepositoryImpl extends BaseJooqRepository implements DepartmentRepository {
    private final DepartmentJpaRepository jpaRepository;

    public DepartmentRepositoryImpl(DepartmentJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public PageResult<DepartmentDTO> queryByPage(DepartmentQueryDTO queryDTO) {
        return super.queryByPage(queryDTO, DepartmentDTO.class);
    }

    @Override
    public List<DepartmentDTO> list() {
        return super.list(DepartmentDTO.class);
    }

    @Override
    public List<DepartmentDTO> list(DepartmentQueryDTO queryDTO) {
        return super.list(queryDTO, DepartmentDTO.class);
    }

    @Override
    public DepartmentDTO FindById(String id) {
        List<DepartmentDTO> list = this.list(DepartmentQueryDTO.builder().id(id).build());
        return list.isEmpty() ? null : list.getFirst();
    }

    @Override
    public Department getById(String id) {
        return jpaRepository.getReferenceById(id);
    }

    @Override
    public void save(Department entity) {
        jpaRepository.save(entity);
    }

    @Override
    public Integer saveAll(List<Department> entities) {
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
        return dsl.select().from(Tables.RES_DEPARTMENT);
    }

    @Override
    protected Table<?> mainTable() {
        return Tables.RES_DEPARTMENT;
    }
}
