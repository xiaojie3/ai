package com.example.systemadministration.controller;

import com.example.edu.common.MyUtils;
import com.example.systemadministration.dto.PermissionDto;
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

    @PostMapping()
    public ResponseEntity<Long> create(@RequestBody PermissionVo vo) {
        PermissionDto dto = new PermissionDto();
        MyUtils.copyProperties(vo,dto);
        dto = service.create(dto).orElseThrow();
        return ResponseEntity.ok(dto.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PermissionVo> findByAccount(@PathVariable String name) {
        PermissionDto dto = service.findByName(name).orElseThrow(() -> new NullPointerException("权限不存在: " + name));
        return ResponseEntity.ok(MyUtils.copyProperties(dto,PermissionVo.class));
    }
}
