package com.example.ai.system.service.impl;

import com.example.ai.common.model.PageResult;
import com.example.ai.common.util.MyUtils;
import com.example.ai.system.dto.SysUserDTO;
import com.example.ai.system.dto.SysUserQueryDTO;
import com.example.ai.system.dto.SysUserSaveDTO;
import com.example.ai.system.entity.SysUser;
import com.example.ai.system.repository.SysUserRepository;
import com.example.ai.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SysUserServiceImpl implements SysUserService {
    private final SysUserRepository repository;

    @Override
    public PageResult<SysUserDTO> queryByPage(@RequestBody SysUserQueryDTO queryDTO) {
        return repository.queryByPage(queryDTO);
    }

    @Override
    public List<SysUserDTO> list() {
        return repository.list();
    }

    @Override
    public List<SysUserDTO> list(SysUserQueryDTO queryDTO) {
        return repository.list();
    }

    @Override
    public SysUserDTO FindById(String id) {
        return repository.FindById(id);
    }

    @Override
    public void save(SysUserSaveDTO saveDTO) {
        repository.save(MyUtils.copyObject(saveDTO, SysUser.class));
    }

    @Override
    public void update(SysUserSaveDTO saveDTO) {
        repository.save(MyUtils.copyObject(saveDTO, SysUser.class));
    }

    @Override
    public void updateNotNll(SysUserSaveDTO saveDTO) {
        repository.save(MyUtils.copyObjectNotNull(saveDTO, SysUser.class));
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

