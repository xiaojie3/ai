package com.example.demo.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.system.mapper.UserMapper;
import com.example.demo.system.model.dto.UserDTO;
import com.example.demo.system.model.dto.UserQueryDTO;
import com.example.demo.system.model.entity.User;
import com.example.demo.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserMapper userMapper;

    @Override
    public IPage<UserDTO> queryByPage(UserQueryDTO queryDTO) {
        // 创建分页参数，并指定排序
        Page<UserDTO> page = new Page<>(1, 20); // 查第1页，每页20条
        page.addOrder(OrderItem.desc("create_time")); // 按创建时间倒序


        LambdaQueryWrapper<User> wrappers = Wrappers.lambdaQuery();

        if(StringUtils.isNotBlank(queryDTO.getId())) {
            wrappers.eq(User::getId, queryDTO.getId());
        }
        if(StringUtils.isNotBlank(queryDTO.getAccount())) {
            wrappers.like(User::getAccount, queryDTO.getAccount());
        }
        if(StringUtils.isNotBlank(queryDTO.getUsername())) {
            wrappers.like(User::getUsername, queryDTO.getUsername());
        }
        /*// 执行分页查询
        Page<User> userPage = this.page(page, wrappers);

        return userPage.convert(user -> {
            UserDTO dto = new UserDTO();
            BeanUtils.copyProperties(user, dto); // 使用 Spring 的工具类
            // 或者用 MapStruct 等更专业的工具
            return dto;
        });*/
        return userMapper.queryByPage(page, wrappers);
    }
}

