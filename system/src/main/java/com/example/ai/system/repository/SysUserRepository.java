package com.example.ai.system.repository;

import com.example.ai.common.model.PageResult;
import com.example.ai.system.dto.SysUserDTO;
import com.example.ai.system.dto.SysUserQueryDTO;
import com.example.ai.system.entity.SysUser;

import java.util.List;

public interface SysUserRepository {
    PageResult<SysUserDTO> queryByPage(SysUserQueryDTO queryDTO);

    List<SysUserDTO> list();

    List<SysUserDTO> list(SysUserQueryDTO queryDTO);

    SysUserDTO FindById(String id);

    SysUser getById(String id);

    void save(SysUser entity);

    void deleteById(String id);

    void deleteByIds(List<String> ids);
}
