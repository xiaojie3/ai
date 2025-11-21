package com.example.demo.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.system.model.entity.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
