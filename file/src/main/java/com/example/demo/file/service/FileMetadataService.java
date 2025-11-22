package com.example.demo.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.file.model.entity.FileMetadata;

import java.util.List;

public interface FileMetadataService extends IService<FileMetadata> {
    List<FileMetadata> findByMd5(String md5);

    FileMetadata findById(String id);
}
