package com.example.demo.file.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.file.mapper.FileMetadataMapper;
import com.example.demo.file.model.entity.FileMetadata;
import com.example.demo.file.service.FileMetadataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileMetadataServiceImpl extends ServiceImpl<FileMetadataMapper, FileMetadata> implements FileMetadataService {
    @Override
    public List<FileMetadata> findByMd5(String md5) {
        return this.lambdaQuery().eq(FileMetadata::getMd5, md5).list();
    }

    @Override
    public FileMetadata findById(String id) {
        return this.getOne(Wrappers.<FileMetadata>lambdaQuery().eq(FileMetadata::getId, id));
    }
}
