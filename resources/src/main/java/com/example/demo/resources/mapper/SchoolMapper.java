package com.example.demo.resources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.resources.model.entity.School;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学校表(ResSchool)表数据库访问层
 *
 * @author robot
 * @since 2025-11-21 14:05:50
 */
@Mapper
public interface SchoolMapper extends BaseMapper<School> {

}

