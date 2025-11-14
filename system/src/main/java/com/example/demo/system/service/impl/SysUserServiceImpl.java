package com.example.demo.system.service.impl;

import com.example.demo.common.model.PageResult;
import com.example.demo.common.util.MyUtils;
import com.example.demo.system.model.dto.SysUserDTO;
import com.example.demo.system.model.dto.SysUserQueryDTO;
import com.example.demo.system.model.dto.SysUserSaveDTO;
import com.example.demo.system.model.entity.SysUser;
import com.example.demo.system.repository.SysUserRepository;
import com.example.demo.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SysUserServiceImpl implements SysUserService {
    private final SysUserRepository repository;
    private final PasswordEncoder passwordEncoder; // 注入 PasswordEncoder

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
        SysUser entity = MyUtils.copyObject(saveDTO, SysUser.class);
        entity.setId(MyUtils.getUUID());
        entity.setPassword(passwordEncoder.encode(saveDTO.getPassword()));
        repository.save(entity);
    }

    @Override
    public void update(SysUserSaveDTO saveDTO) {
        repository.save(MyUtils.copyObject(saveDTO, SysUser.class));
    }

    @Override
    public void updateNotNll(SysUserSaveDTO saveDTO) {
        SysUser entity = repository.getById(saveDTO.getId());
        MyUtils.copyObject(saveDTO, entity, false);
        if(StringUtils.isNotBlank(entity.getPassword())) {
            entity.setPassword(passwordEncoder.encode(saveDTO.getPassword()));
        }
        entity.setUpdateTime(LocalDateTime.now());
        repository.save(entity);
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

