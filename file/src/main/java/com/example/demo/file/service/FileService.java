package com.example.demo.file.service;

import com.example.demo.file.model.dto.FileDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    FileDTO upload(MultipartFile file, String directoryId, String userId) throws IOException;

    boolean checkDownloadPermission(String fileId, String userId, List<String> userRoleCodeList);

    void delete(String fileId, String userId) throws IOException;

    void download(String fileId, String userId, HttpServletResponse response) throws IOException;

    FileDTO uploadAvatar(MultipartFile file, String userId) throws IOException;
}
