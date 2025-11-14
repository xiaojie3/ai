package com.example.demo.system.repository;

import com.example.demo.common.model.PageResult;
import com.example.demo.system.model.entity.SysRole;
import com.example.demo.system.model.dto.SysRoleDTO;
import com.example.demo.system.model.dto.SysRoleQueryDTO;

import java.util.List;

public interface SysRoleRepository {
    PageResult<SysRoleDTO> queryByPage(SysRoleQueryDTO queryDTO);

    List<SysRoleDTO> list();

    List<SysRoleDTO> list(SysRoleQueryDTO queryDTO);

    SysRoleDTO FindById(String id);

    SysRole getById(String id);

    void save(SysRole entity);

    void deleteById(String id);

    void deleteByIds(List<String> ids);
}
