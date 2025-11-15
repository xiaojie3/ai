package com.example.demo.file.strategy;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface FileStorageStrategy {
    String upload(MultipartFile file, String relativePath) throws IOException;
    InputStream download(String filePath) throws IOException;
    boolean delete(String filePath) throws IOException;
}
