package com.example.demo.file.service.impl;

import com.example.demo.common.util.MyUtils;
import com.example.demo.file.model.dto.FileDTO;
import com.example.demo.file.model.entity.FileDirectoryRole;
import com.example.demo.file.model.entity.FileMetadata;
import com.example.demo.file.model.enums.FileDirectoryEnum;
import com.example.demo.file.service.FileDirectoryRoleService;
import com.example.demo.file.service.FileMetadataService;
import com.example.demo.file.service.FileService;
import com.example.demo.file.strategy.FileStorageStrategy;
import com.example.demo.file.util.ImageUtils;
import com.example.demo.file.util.Md5Util;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileStorageStrategy fileStorageStrategy;
    private final FileMetadataService fileMetadataService;
    private final FileDirectoryRoleService fileDirectoryRoleService;
    private final ImageUtils imageUtils;

    @Override
    public FileDTO upload(MultipartFile file, String directoryId, String userId) throws IOException {
        // 1. 计算文件MD5（防重复上传）
        String md5 = Md5Util.calculateMd5(file.getInputStream());
        List<FileMetadata> existFileList = fileMetadataService.findByMd5(md5);
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
        fileMetadata.setId(MyUtils.getUUID());
        fileMetadata.setFileName(file.getOriginalFilename());
        fileMetadata.setFilePath(filePath);
        fileMetadata.setFileSize(file.getSize());
        fileMetadata.setFileType(file.getContentType());
        fileMetadata.setUploaderId(userId);
        fileMetadata.setDirectoryId(directoryId);
        fileMetadata.setMd5(md5);
        fileMetadata.setCreateTime(LocalDateTime.now());
        fileMetadataService.save(fileMetadata);

        return convertToDTO(fileMetadata);
    }

    @Override
    public boolean checkDownloadPermission(String fileId, String userId, List<String> userRoleCodeList) {
        // 1. 查询文件元数据
        FileMetadata fileMetadata = fileMetadataService.findById(fileId);

        // 2. 条件1：上传者专属下载
        if (userId.equals(fileMetadata.getUploaderId())) {
            return true;
        }

        // 3. 条件2：目录角色授权（用户角色包含目录授权角色）
        String directoryId = fileMetadata.getDirectoryId();
        List<FileDirectoryRole> authList = fileDirectoryRoleService.lambdaQuery().eq(FileDirectoryRole::getDirectoryId, directoryId).list();

        // 检查用户角色是否与授权角色匹配
        Set<String> authRoles = authList.stream().map(FileDirectoryRole::getRoleId).collect(Collectors.toSet());
        return userRoleCodeList.stream().anyMatch(authRoles::contains);
    }

    @Override
    public void delete(String fileId, String userId) throws IOException {
        FileMetadata fileMetadata = fileMetadataService.findById(fileId);
        if(userId.equals(fileMetadata.getUploaderId())) {
            fileStorageStrategy.delete(fileMetadata.getFilePath());
        }
    }

    /**
     * 文件下载：读取存储系统文件，写入响应流
     */
    @Override
    public void download(String fileId, String userId, HttpServletResponse response) throws IOException {
        FileMetadata fileMetadata = fileMetadataService.findById(fileId);
        if(userId.equals(fileMetadata.getUploaderId())) {
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
    }

    @Override
    public FileDTO uploadAvatar(MultipartFile file, String userId) throws IOException {
        // 1. 图片合法性校验（尺寸、格式、大小）
        imageUtils.validateImage(file);
        // 2. 生成多尺寸头像（200x200、400x400、原图）
        try (InputStream originalStream = new BufferedInputStream(file.getInputStream())) {
            Map<String, byte[]> avatarMap = imageUtils.generateAvatarSizes(originalStream);

            // 3. 存储各尺寸头像并保存元数据
            Map<String, FileDTO> resultMap = new HashMap<>();
            for (Map.Entry<String, byte[]> entry : avatarMap.entrySet()) {
                String size = entry.getKey();
                byte[] imageBytes = entry.getValue();

                // 3.1 计算MD5（防重复）
                String md5 = Md5Util.calculateMd5(new ByteArrayInputStream(imageBytes));
                List<FileMetadata> existFileList = fileMetadataService.findByMd5(md5);
                if (!existFileList.isEmpty()) {
                    return convertToDTO(existFileList.getFirst());
                }

                // 3.2 构建存储路径（头像目录/用户ID/尺寸/文件名.webp）
                String fileName = String.format("%s_%s.%s", userId, size, "webp");
                String relativePath = String.format("%s/%s/%s/%s",
                        FileDirectoryEnum.AVATAR.getDirName(),
                        userId,
                        size,
                        fileName);

                // 3.3 存储图片
                String filePath = fileStorageStrategy.upload(file, relativePath);

                // 3.4 保存图片元数据
                FileMetadata fileMetadata = new FileMetadata();
                fileMetadata.setId(MyUtils.getUUID());
                fileMetadata.setFileName(fileName);
                fileMetadata.setFilePath(filePath);
                fileMetadata.setFileSize((long) imageBytes.length);
                fileMetadata.setFileType("image/webp");
                fileMetadata.setUploaderId(userId);
                fileMetadata.setDirectoryId(FileDirectoryEnum.AVATAR.getDirName());
                fileMetadata.setMd5(md5);
                fileMetadata.setCreateTime(LocalDateTime.now());
                fileMetadataService.save(fileMetadata);

                resultMap.put(size, convertToDTO(fileMetadata));
            }
            return resultMap.get("200x200");
        }
    }

    private FileDTO convertToDTO(FileMetadata Metadata) {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setId(Metadata.getId());
        return fileDTO;
    }
}
