package com.example.demo.resources.repository.impl;

import com.example.demo.common.model.PageResult;
import com.example.demo.resources.model.dto.CampusDTO;
import com.example.demo.resources.model.dto.CampusQueryDTO;
import com.example.demo.resources.model.entity.Campus;
import com.example.demo.resources.repository.CampusJpaRepository;
import com.example.demo.resources.repository.CampusRepository;
import com.example.demo.resources.repository.jooq.Tables;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CampusRepositoryImpl extends BaseJooqRepository implements CampusRepository {
    private final CampusJpaRepository jpaRepository;

    public CampusRepositoryImpl(CampusJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public PageResult<CampusDTO> queryByPage(CampusQueryDTO queryDTO) {
        return super.queryByPage(queryDTO, CampusDTO.class);
    }

    @Override
    public List<CampusDTO> list() {
        return super.list(CampusDTO.class);
    }

    @Override
    public List<CampusDTO> list(CampusQueryDTO queryDTO) {
        return super.list(queryDTO, CampusDTO.class);
    }

    @Override
    public CampusDTO FindById(String id) {
        List<CampusDTO> list = this.list(CampusQueryDTO.builder().id(id).build());
        return list.isEmpty() ? null : list.getFirst();
    }

    @Override
    public Campus getById(String id) {
        return jpaRepository.getReferenceById(id);
    }

    @Override
    public void save(Campus entity) {
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
        return sqlSelect.from(Tables.RES_CAMPUS)
                .leftJoin(Tables.RES_SCHOOL).on(Tables.RES_CAMPUS.SCHOOL_ID.eq(Tables.RES_SCHOOL.ID));
    }

    @Override
    protected Table<?> mainTable() {
        return Tables.RES_CAMPUS;
    }
}

