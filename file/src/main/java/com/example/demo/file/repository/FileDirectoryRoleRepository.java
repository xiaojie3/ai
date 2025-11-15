package com.example.demo.file.repository;

import com.example.demo.file.model.entity.FileDirectoryRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDirectoryRoleRepository extends JpaRepository<FileDirectoryRole, String> {

    List<FileDirectoryRole> findByDirectoryId(String directoryId);
}
