package com.example.demo.system.service.impl;

import com.example.demo.common.model.PageResult;
import com.example.demo.common.util.MyUtils;
import com.example.demo.system.model.dto.SysRoleDTO;
import com.example.demo.system.model.dto.SysRoleQueryDTO;
import com.example.demo.system.model.dto.SysRoleSaveDTO;
import com.example.demo.system.model.entity.SysRole;
import com.example.demo.system.repository.SysRoleRepository;
import com.example.demo.system.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SysRoleServiceImpl implements SysRoleService {
    private final SysRoleRepository repository;

    @Override
    public PageResult<SysRoleDTO> queryByPage(@RequestBody SysRoleQueryDTO queryDTO) {
        return repository.queryByPage(queryDTO);
    }

    @Override
    public List<SysRoleDTO> list() {
        return repository.list();
    }

    @Override
    public List<SysRoleDTO> list(SysRoleQueryDTO queryDTO) {
        return repository.list();
    }

    @Override
    public SysRoleDTO FindById(String id) {
        return repository.FindById(id);
    }

    @Override
    public void save(SysRoleSaveDTO saveDTO) {
        repository.save(MyUtils.copyObject(saveDTO, SysRole.class));
    }

    @Override
    public void update(SysRoleSaveDTO saveDTO) {
        repository.save(MyUtils.copyObject(saveDTO, SysRole.class));
    }

    @Override
    public void updateNotNll(SysRoleSaveDTO saveDTO) {
        repository.save(MyUtils.copyObjectNotNull(saveDTO, SysRole.class));
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        repository.deleteByIds(ids);
    }
}

