package com.example.demo.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.file.model.entity.FileMetadata;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMetadataMapper extends BaseMapper<FileMetadata> {
}
