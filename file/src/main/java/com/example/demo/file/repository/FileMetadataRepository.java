package com.example.demo.file.repository;

import com.example.demo.file.model.entity.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadata, String> {

    List<FileMetadata> findByMd5(String md5);
}
