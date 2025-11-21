package com.example.demo.resources.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.resources.mapper.SchoolMapper;
import com.example.demo.resources.model.entity.School;
import com.example.demo.resources.service.SchoolService;
import org.springframework.stereotype.Service;

/**
 * 学校表(ResSchool)表服务实现类
 *
 * @author robot
 * @since 2025-11-21 14:05:51
 */
@Service("resSchoolService")
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements SchoolService {

}

