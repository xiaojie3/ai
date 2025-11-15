package com.example.demo.file.service;

import com.example.demo.file.model.dto.FileDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    FileDTO upload(MultipartFile file, String directoryId, String uploaderId) throws IOException;

    boolean checkDownloadPermission(String fileId, String userId, List<String> userRoleCodes);

    void download(String fileId, HttpServletResponse response) throws IOException;
}
