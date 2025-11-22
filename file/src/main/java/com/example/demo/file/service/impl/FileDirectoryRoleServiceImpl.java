package com.example.demo.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.file.mapper.FileDirectoryRoleMapper;
import com.example.demo.file.model.entity.FileDirectoryRole;
import com.example.demo.file.service.FileDirectoryRoleService;
import org.springframework.stereotype.Service;

@Service
public class FileDirectoryRoleServiceImpl extends ServiceImpl<FileDirectoryRoleMapper, FileDirectoryRole> implements FileDirectoryRoleService {
}
