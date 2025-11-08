package com.example.ai.system.repository.impl;

import com.example.ai.common.model.BaseJooqRepository;
import com.example.ai.common.model.PageResult;
import com.example.ai.system.dto.SysUserDTO;
import com.example.ai.system.dto.SysUserQueryDTO;
import com.example.ai.common.model.entity.SysUser;
import com.example.ai.system.repository.jooq.Tables;
import com.example.ai.system.repository.SysUserJpaRepository;
import com.example.ai.system.repository.SysUserRepository;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SysUserRepositoryImpl extends BaseJooqRepository implements SysUserRepository {
    private final SysUserJpaRepository jpaRepository;

    public SysUserRepositoryImpl(SysUserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public PageResult<SysUserDTO> queryByPage(SysUserQueryDTO queryDTO) {
        return super.queryByPage(queryDTO, SysUserDTO.class);
    }

    @Override
    public List<SysUserDTO> list() {
        return super.list(SysUserDTO.class);
    }

    @Override
    public List<SysUserDTO> list(SysUserQueryDTO queryDTO) {
        return super.list(queryDTO, SysUserDTO.class);
    }

    @Override
    public SysUserDTO FindById(String id) {
        List<SysUserDTO> list = this.list(SysUserQueryDTO.builder().id(id).build());
        return list.isEmpty() ? null : list.getFirst();
    }

    @Override
    public SysUser getById(String id) {
        return jpaRepository.getReferenceById(id);
    }

    @Override
    public void save(SysUser entity) {
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
        return dsl.select().from(Tables.SYS_USER);
    }

    @Override
    protected Table<?> mainTable() {
        return Tables.SYS_USER;
    }
}


