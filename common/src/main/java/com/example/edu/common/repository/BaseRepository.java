package com.example.edu.common.repository;

import com.example.edu.common.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository<E extends BaseEntity> extends JpaRepository<E, Long> {

}
