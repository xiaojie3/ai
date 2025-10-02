package com.example.systemadministration.mapper;

import com.example.edu.common.dto.UserDto;
import com.example.systemadministration.entity.UserEntity;
import com.example.systemadministration.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper // 声明这是一个 MapStruct Mapper
public interface UserMapper {

    // 获取 Mapper 的单例实例
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(UserEntity entity);

    UserDto toDto(UserVo vo);

    UserEntity toEntity(UserDto dto);

    UserVo toVo(UserDto dto);
}
