package com.example.demo.file.strategy.impl;

import com.example.demo.file.config.FileStorageConfig;
import com.example.demo.file.strategy.FileStorageStrategy;
import io.minio.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * MinIO 存储策略实现（兼容 FileStorageStrategy 接口）
 */
@Component
public class MinIOStorageStrategy implements FileStorageStrategy {

    private final MinioClient minioClient;
    private final FileStorageConfig.MinioConfig minioConfig;

    // 构造函数：初始化 MinIO 客户端
    public MinIOStorageStrategy(FileStorageConfig fileStorageConfig) {
        this.minioConfig = fileStorageConfig.getMinio();
        // 初始化 MinIO 客户端（基于配置）
        this.minioClient = MinioClient.builder()
                .endpoint(minioConfig.getEndpoint())
                .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                .region(minioConfig.getRegion())
                .build();
    }

    /**
     * 上传文件到 MinIO
     * @param file 待上传文件
     * @param relativePath 相对路径（如 "avatar/1001/400x400/123.webp"）
     * @return MinIO 中的文件标识（格式："桶名/相对路径"，用于后续下载）
     */
    @Override
    public String upload(MultipartFile file, String relativePath) throws IOException {
        try {
            // 1. 确定存储桶（根据相对路径判断业务：头像/普通文件）
            String bucketName = getBucketNameByRelativePath(relativePath);

            // 2. 校验桶是否存在，不存在则创建
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(io.minio.MakeBucketArgs.builder().bucket(bucketName).build());
            }

            // 3. 上传文件到 MinIO（PutObjectArgs 配置）
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName) // 存储桶
                            .object(relativePath) // 文件在桶中的路径（含文件名）
                            .stream(file.getInputStream(), file.getSize(), -1) // 数据流（-1 表示自动识别文件大小）
                            .contentType(file.getContentType()) // 文件类型（如 image/webp、application/pdf）
                            .build()
            );

            // 4. 返回文件标识（桶名/相对路径，用于下载时解析）
            return String.format("%s/%s", bucketName, relativePath);
        } catch (Exception e) {
            throw new IOException("MinIO 上传文件失败：" + e.getMessage(), e);
        }
    }

    /**
     * 从 MinIO 下载文件
     * @param filePath MinIO 中的文件标识（upload 方法返回的 "桶名/相对路径"）
     * @return 文件输入流（供业务层写入响应流）
     */
    @Override
    public InputStream download(String filePath) throws IOException {
        try {
            // 解析文件标识：拆分桶名和对象路径（格式：bucketName/objectPath）
            String[] parts = filePath.split("/", 2);
            if (parts.length != 2) {
                throw new IOException("MinIO 文件路径格式错误：" + filePath);
            }
            String bucketName = parts[0];
            String objectPath = parts[1];

            // 从 MinIO 获取文件流
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectPath)
                            .build()
            );
        } catch (Exception e) {
            throw new IOException("MinIO 下载文件失败：" + e.getMessage(), e);
        }
    }

    /**
     * 从 MinIO 删除文件
     * @param filePath MinIO 中的文件标识（"桶名/相对路径"）
     * @return 是否删除成功
     */
    @Override
    public boolean delete(String filePath) throws IOException {
        try {
            String[] parts = filePath.split("/", 2);
            if (parts.length != 2) {
                throw new IOException("MinIO 文件路径格式错误：" + filePath);
            }
            String bucketName = parts[0];
            String objectPath = parts[1];

            // 删除 MinIO 中的文件
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectPath)
                            .build()
            );
            return true;
        } catch (Exception e) {
            throw new IOException("MinIO 删除文件失败：" + e.getMessage(), e);
        }
    }

    /**
     * 辅助方法：根据相对路径判断业务，选择对应的存储桶
     * （可根据实际业务调整，如头像路径含 "avatar" 则用 user-avatar 桶）
     */
    private String getBucketNameByRelativePath(String relativePath) {
        if (relativePath.contains("avatar")) {
            return minioConfig.getBucket().getAvatar(); // 头像桶
        } else {
            return minioConfig.getBucket().getCommon(); // 普通文件桶
        }
    }
}