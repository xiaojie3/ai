package com.example.ai.system.repository;

import com.example.ai.common.model.PageResult;
import com.example.ai.system.entity.SysRole;
import com.example.ai.system.dto.SysRoleDTO;
import com.example.ai.system.dto.SysRoleQueryDTO;

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
