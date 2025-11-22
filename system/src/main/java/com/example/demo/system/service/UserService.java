package com.example.demo.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.system.model.dto.UserQueryDTO;
import com.example.demo.system.model.dto.UserDTO;
import com.example.demo.system.model.entity.User;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService extends IService<User> {
    IPage<UserDTO> queryByPage(@RequestBody UserQueryDTO queryDTO);
}
