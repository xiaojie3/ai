package com.example.demo.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.system.model.dto.UserDTO;
import com.example.demo.system.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 分页查询：接收 IPage 分页参数 + LambdaQueryWrapper 条件
    IPage<UserDTO> queryByPage(Page<UserDTO> page,
                               @Param(Constants.WRAPPER) LambdaQueryWrapper<User> queryWrapper);
}
