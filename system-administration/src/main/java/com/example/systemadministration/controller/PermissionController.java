package com.example.systemadministration.controller;

import com.example.edu.common.dto.PermissionDto;
import com.example.systemadministration.mapper.PermissionMapper;
import com.example.systemadministration.service.PermissionService;
import com.example.systemadministration.vo.PermissionVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/permission")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService service;
    private final PermissionMapper mapper;

    @PostMapping()
    public ResponseEntity<Long> create(@RequestBody PermissionVo vo) {
        PermissionDto dto = service.create(mapper.toDto(vo)).orElseThrow();
        return ResponseEntity.ok(dto.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PermissionVo> findByAccount(@PathVariable String name) {
        PermissionDto dto = service.findByName(name).orElseThrow();
        return ResponseEntity.ok(mapper.toVo(dto));
    }
}
