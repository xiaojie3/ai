package com.example.demo.file.service.impl;

import com.example.demo.common.util.JwtTokenUtil;
import com.example.demo.file.model.dto.FileDTO;
import com.example.demo.file.model.entity.FileDirectoryRole;
import com.example.demo.file.model.entity.FileMetadata;
import com.example.demo.file.repository.FileDirectoryRoleRepository;
import com.example.demo.file.repository.FileMetadataRepository;
import com.example.demo.file.service.FileService;
import com.example.demo.file.strategy.FileStorageStrategy;
import com.example.demo.file.util.Md5Util;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final MessageSource messageSource;
    private final FileStorageStrategy fileStorageStrategy;
    private final FileMetadataRepository fileMetadataRepository;
    private final FileDirectoryRoleRepository fileDirectoryRoleRepository;

    @Override
    public FileDTO upload(MultipartFile file, String directoryId, String userId) throws IOException {
        // 1. 计算文件MD5（防重复上传）
        String md5 = Md5Util.calculateMd5(file.getInputStream());
        List<FileMetadata> existFileList = fileMetadataRepository.findByMd5(md5);
        if (!existFileList.isEmpty()) {
            return convertToDTO(existFileList.getFirst());
        }
        // 2. 构建存储路径（目录ID/年月日/文件名）
        String relativePath = String.format("%s/%s/%s",
                directoryId,
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                System.currentTimeMillis() + "_" + file.getOriginalFilename());

        // 3. 上传文件到存储系统
        String filePath = fileStorageStrategy.upload(file, relativePath);

        // 4. 保存文件元数据
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setFileName(file.getOriginalFilename());
        fileMetadata.setFilePath(filePath);
        fileMetadata.setFileSize(file.getSize());
        fileMetadata.setFileType(file.getContentType());
        fileMetadata.setUploaderId(userId);
        fileMetadata.setDirectoryId(directoryId);
        fileMetadata.setMd5(md5);
        fileMetadata.setCreateTime(LocalDateTime.now());
        fileMetadataRepository.save(fileMetadata);

        return convertToDTO(fileMetadata);
    }

    @Override
    public boolean checkDownloadPermission(String fileId, String userId, List<String> userRoleCodeList) {
        // 1. 查询文件元数据
        FileMetadata fileMetadata = fileMetadataRepository.findById(fileId).orElseThrow(() -> {
            String msg = messageSource.getMessage("file.notExists", null, LocaleContextHolder.getLocale());
            return new RuntimeException(msg);
        });

        // 2. 条件1：上传者专属下载
        if (userId.equals(fileMetadata.getUploaderId())) {
            return true;
        }

        // 3. 条件2：目录角色授权（用户角色包含目录授权角色）
        String directoryId = fileMetadata.getDirectoryId();
        List<FileDirectoryRole> authList = fileDirectoryRoleRepository.findByDirectoryId(directoryId);

        // 检查用户角色是否与授权角色匹配
        Set<String> authRoles = authList.stream().map(FileDirectoryRole::getRoleId).collect(Collectors.toSet());
        return userRoleCodeList.stream().anyMatch(authRoles::contains);
    }

    @Override
    public void delete(String fileId) throws IOException {
        FileMetadata fileMetadata = fileMetadataRepository.findById(fileId).orElseThrow(() -> {
            String msg = messageSource.getMessage("file.notExists", null, LocaleContextHolder.getLocale());
            return new RuntimeException(msg);
        });
        fileStorageStrategy.delete(fileMetadata.getFilePath());
    }

    /**
     * 文件下载：读取存储系统文件，写入响应流
     */
    @Override
    public void download(String fileId, HttpServletResponse response) throws IOException {
        FileMetadata fileMetadata = fileMetadataRepository.findById(fileId).orElseThrow(() -> {
            String msg = messageSource.getMessage("file.notExists", null, LocaleContextHolder.getLocale());
            return new RuntimeException(msg);
        });

        // 设置响应头
        response.setContentType(fileMetadata.getFileType());
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileMetadata.getFileName(), StandardCharsets.UTF_8));
        response.setContentLengthLong(fileMetadata.getFileSize());

        // 读取文件并写入响应流
        try (InputStream inputStream = fileStorageStrategy.download(fileMetadata.getFilePath());
             OutputStream outputStream = response.getOutputStream()) {
            IOUtils.copy(inputStream, outputStream);
        }
    }

    private FileDTO convertToDTO(FileMetadata Metadata) {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setId(Metadata.getId());
        return fileDTO;
    }
}
