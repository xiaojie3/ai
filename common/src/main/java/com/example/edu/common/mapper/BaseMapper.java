package com.example.edu.common.mapper;

import com.example.edu.common.dto.BaseDto;
import com.example.edu.common.entity.BaseEntity;
import com.example.edu.common.vo.BaseVo;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface BaseMapper<Entity extends BaseEntity, Dto extends BaseDto, Vo extends BaseVo> {

    // 从 Entity 转换为 Dto
    Dto toDto(Entity entity);

    // 从 Dto 转换为 Entity
    Entity toEntity(Dto dto);

    // 从 Dto 转换为 Vo
    Vo toVo(Dto dto);

    // 从 Vo 转换为 Dto
    Dto toDto(Vo vo);
}
