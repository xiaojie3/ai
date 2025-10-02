package com.example.edu.common.service.impl;
import com.example.edu.common.dto.BaseDto;
import com.example.edu.common.entity.BaseEntity;
import com.example.edu.common.mapper.BaseMapper;
import com.example.edu.common.service.BaseService;
import com.example.edu.common.vo.BaseVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public abstract class BaseServiceImpl<Entity extends BaseEntity, Dto extends BaseDto, Vo extends BaseVo> implements BaseService<Dto> {

    // 子类需要提供具体的 Repository
    protected abstract JpaRepository<Entity, Long> getRepository();

    // 子类需要提供具体的 Mapper
    protected abstract BaseMapper<Entity, Dto, Vo> getMapper();

    @Transactional(readOnly = true)
    public Page<Dto> findAll(Pageable pageable) {
        return getRepository().findAll(pageable).map((entity) ->getMapper().toDto(entity));
    }

    @Transactional(readOnly = true)
    public Optional<Dto> findById(Long id) {
        Entity entity = getRepository().getReferenceById(id);
        return Optional.of(getMapper().toDto(entity));
    }

    @Transactional
    public Optional<Dto> create(Dto dto) {
        Entity entity = getMapper().toEntity(dto);
        // 注意：creator 和 createDate 会被自动填充，无需手动设置
        Entity savedEntity = getRepository().save(entity);
        return Optional.of(getMapper().toDto(savedEntity));
    }

    @Transactional
    public Optional<Dto> update(Long id, Dto dto) {
        return getRepository().findById(id).map(existingEntity -> {
            // 将 DTO 的数据更新到已存在的实体上
            // 注意：需要一个好的 Mapper 实现，只更新非 null 的字段
            Entity updatedEntity = getMapper().toEntity(dto);
            // 确保 ID 不变
            // (假设你的 Mapper 会处理好这一点，或者在这里手动设置)

            // modifier 和 modifyDate 会被自动填充
            Entity savedEntity = getRepository().save(updatedEntity);
            return getMapper().toDto(savedEntity);
        });
    }

    @Transactional
    public void deleteById(Long id) {
        getRepository().deleteById(id);
    }
}
