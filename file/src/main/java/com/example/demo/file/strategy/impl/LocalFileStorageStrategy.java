package com.example.demo.file.strategy.impl;

import com.example.demo.file.config.FileStorageConfig;
import com.example.demo.file.strategy.FileStorageStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class LocalFileStorageStrategy implements FileStorageStrategy {
    private final FileStorageConfig config;

    public LocalFileStorageStrategy(FileStorageConfig config) {
        this.config = config;
        // 初始化根目录
        File baseDir = new File(config.getLocalBasePath());
        if (!baseDir.exists()) baseDir.mkdirs();
    }

    @Override
    public String upload(MultipartFile file, String relativePath) throws IOException {
        String fullPath = config.getLocalBasePath() + relativePath;
        File destFile = new File(fullPath);
        // 创建父目录
        if (!destFile.getParentFile().exists()) destFile.getParentFile().mkdirs();
        file.transferTo(destFile);
        return fullPath;
    }

    @Override
    public InputStream download(String filePath) throws IOException {
        return new FileInputStream(filePath);
    }

    @Override
    public boolean delete(String filePath) throws IOException {
        return new File(filePath).delete();
    }
}
