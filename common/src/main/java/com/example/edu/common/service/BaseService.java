package com.example.edu.common.service;

import com.example.edu.common.dto.BaseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BaseService<Dto extends BaseDto> {

    Page<Dto> findAll(Pageable pageable);

    Optional<Dto> findById(Long id);

    Optional<Dto> create(Dto dto);

    Optional<Dto> update(Long id, Dto dto);

    void deleteById(Long id);
}
