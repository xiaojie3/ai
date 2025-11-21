package com.example.demo.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.system.model.entity.RoleGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleGroupMapper extends BaseMapper<RoleGroup> {
    List<RoleGroup> selectTopLevelGroups();
}
