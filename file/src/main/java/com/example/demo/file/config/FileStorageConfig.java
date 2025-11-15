package com.example.demo.file.config;

import com.example.demo.file.strategy.FileStorageStrategy;
import com.example.demo.file.strategy.impl.LocalFileStorageStrategy;
import com.example.demo.file.strategy.impl.MinIOStorageStrategy;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file.storage")
@Data
public class FileStorageConfig {
    private String type; // local/minio（切换存储方式）

    // 本地存储配置
    private String localBasePath;

    // MinIO 存储配置（新增）
    private MinioConfig minio;

    // 内部类：MinIO 子配置
    @Data
    public static class MinioConfig {
        private String endpoint;
        private String accessKey;
        private String secretKey;
        private MinioBucketConfig bucket; // 存储桶配置
        private String region;
        private boolean secure; // 是否 HTTPS
    }

    // 内部类：MinIO 存储桶细分配置（按业务区分）
    @Data
    public static class MinioBucketConfig {
        private String common; // 普通文件桶
        private String avatar; // 头像文件桶
    }

    // 存储策略 Bean（根据 type 动态选择 Local/MinIO 存储）
    @Bean
    public FileStorageStrategy fileStorageStrategy() {
        if ("minio".equalsIgnoreCase(type)) {
            return new MinIOStorageStrategy(this); // MinIO 存储策略
        } else {
            return new LocalFileStorageStrategy(this); // 本地存储策略
        }
    }
}
