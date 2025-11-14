package com.example.demo.system.service;

import com.example.demo.common.model.PageResult;
import com.example.demo.system.model.dto.SysRoleDTO;
import com.example.demo.system.model.dto.SysRoleQueryDTO;
import com.example.demo.system.model.dto.SysRoleSaveDTO;

import java.util.List;

public interface SysRoleService {

    PageResult<SysRoleDTO> queryByPage(SysRoleQueryDTO queryDTO);

    List<SysRoleDTO> list();

    List<SysRoleDTO> list(SysRoleQueryDTO queryDTO);

    SysRoleDTO FindById(String id);

    void save(SysRoleSaveDTO saveDTO);

    void update(SysRoleSaveDTO saveDTO);

    void updateNotNll(SysRoleSaveDTO saveDTO);

    void deleteById(String id);

    void deleteByIds(List<String> ids);
}
