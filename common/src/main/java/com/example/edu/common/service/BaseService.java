package com.example.edu.common.service;

import com.example.edu.common.dto.BaseDto;
import com.example.edu.common.entity.BaseEntity;
import com.example.edu.common.vo.BaseVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BaseService<Entity extends BaseEntity, Dto extends BaseDto> {

    Page<Dto> findAll(Pageable pageable);

    Optional<Dto> findById(Long id);

    Optional<Dto> create(Dto dto);

    Optional<Dto> update(Long id, Dto dto);

    void deleteById(Long id);

    // 从 Entity 转换为 Dto
    Dto toDto(Entity entity);

    // 从 Dto 转换为 Entity
    Entity toEntity(Dto dto);
/*
    // 从 Dto 转换为 Vo
    <Vo extends BaseVo> Vo toVo(Dto dto);

    // 从 Vo 转换为 Dto
    <Vo extends BaseVo> Dto toDto(Vo vo);*/
}
