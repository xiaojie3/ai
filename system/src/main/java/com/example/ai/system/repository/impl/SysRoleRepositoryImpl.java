package com.example.ai.system.repository.impl;

import com.example.ai.common.model.BaseJooqRepository;
import com.example.ai.common.model.PageResult;
import com.example.ai.system.model.dto.SysRoleDTO;
import com.example.ai.system.model.dto.SysRoleQueryDTO;
import com.example.ai.system.model.entity.SysRole;
import com.example.ai.system.repository.jooq.Tables;
import com.example.ai.system.repository.SysRoleJpaRepository;
import com.example.ai.system.repository.SysRoleRepository;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SysRoleRepositoryImpl extends BaseJooqRepository implements SysRoleRepository {
    private final SysRoleJpaRepository jpaRepository;

    public SysRoleRepositoryImpl(SysRoleJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public PageResult<SysRoleDTO> queryByPage(SysRoleQueryDTO queryDTO) {
        return super.queryByPage(queryDTO, SysRoleDTO.class);
    }

    @Override
    public List<SysRoleDTO> list() {
        return super.list(SysRoleDTO.class);
    }

    @Override
    public List<SysRoleDTO> list(SysRoleQueryDTO queryDTO) {
        return super.list(queryDTO, SysRoleDTO.class);
    }

    @Override
    public SysRoleDTO FindById(String id) {
        List<SysRoleDTO> list = this.list(SysRoleQueryDTO.builder().id(id).build());
        return list.isEmpty() ? null : list.getFirst();
    }

    @Override
    public SysRole getById(String id) {
        return jpaRepository.getReferenceById(id);
    }

    @Override
    public void save(SysRole entity) {
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
        return dsl.select().from(Tables.SYS_ROLE);
    }

    @Override
    protected Table<?> mainTable() {
        return Tables.SYS_ROLE;
    }
}


