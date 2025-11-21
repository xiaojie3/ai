package com.example.demo.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.auth.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<String> getRoleCodeListByUserId(@Param("userId") String userId);
}
