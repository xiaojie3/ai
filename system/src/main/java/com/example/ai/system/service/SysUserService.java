package com.example.ai.system.service;

import com.example.ai.common.model.PageResult;
import com.example.ai.system.model.dto.SysUserDTO;
import com.example.ai.system.model.dto.SysUserQueryDTO;
import com.example.ai.system.model.dto.SysUserSaveDTO;

import java.util.List;

public interface SysUserService {

    PageResult<SysUserDTO> queryByPage(SysUserQueryDTO queryDTO);

    List<SysUserDTO> list();

    List<SysUserDTO> list(SysUserQueryDTO queryDTO);

    SysUserDTO FindById(String id);

    void save(SysUserSaveDTO saveDTO);

    void update(SysUserSaveDTO saveDTO);

    void updateNotNll(SysUserSaveDTO saveDTO);

    void deleteById(String id);

    void deleteByIds(List<String> ids);
}
