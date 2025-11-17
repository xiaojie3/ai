package com.example.demo.resources.controller;

import com.example.demo.common.model.ApiResult;
import com.example.demo.common.model.PageResult;
import com.example.demo.resources.model.dto.*;
import com.example.demo.resources.service.CampusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resource/campus")
@RequiredArgsConstructor
@Tag(name = "校区表")
public class CampusController {

    private final CampusService service;

    /**
     * 分页查询
     *
     * @param queryDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("/query")
    @Operation(summary = "分页查询")
    public ResponseEntity<ApiResult<PageResult<CampusDTO>>> queryByPage(@RequestBody CampusQueryDTO queryDTO) {
        return ResponseEntity.ok(ApiResult.success(this.service.queryByPage(queryDTO)));
    }

    /**
     * 列表查询
     *
     * @param queryDTO 筛选条件
     * @return 查询结果
     */
    @GetMapping()
    @Operation(summary = "列表查询")
    public ResponseEntity<ApiResult<List<CampusDTO>>> queryByList(CampusQueryDTO queryDTO) {
        return ResponseEntity.ok(ApiResult.success(this.service.list(queryDTO)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    @Operation(summary = "ID查询")
    public ResponseEntity<ApiResult<CampusDTO>> queryById(@PathVariable("id") String id) {
        return ResponseEntity.ok(ApiResult.success(this.service.FindById(id)));
    }

    /**
     * 新增数据
     *
     * @param saveDTO 实体
     * @return 新增结果
     */
    @PostMapping
    @Operation(summary = "新增")
    public ResponseEntity<ApiResult<CampusSaveDTO>> add(CampusSaveDTO saveDTO) {
        this.service.save(saveDTO);
        return ResponseEntity.ok(ApiResult.success(saveDTO));
    }

    /**
     * 编辑数据
     *
     * @param saveDTO 实体
     * @return 编辑结果
     */
    @PutMapping
    @Operation(summary = "编辑")
    public ResponseEntity<ApiResult<CampusSaveDTO>> edit(CampusSaveDTO saveDTO) {
        this.service.update(saveDTO);
        return ResponseEntity.ok(ApiResult.success(saveDTO));
    }

    /**
     * 编辑数据
     *
     * @param saveDTO 实体
     * @return 编辑结果
     */
    @PutMapping("/notNull")
    @Operation(summary = "编辑非空参数")
    public ResponseEntity<ApiResult<CampusSaveDTO>> editByNotNull(CampusSaveDTO saveDTO) {
        this.service.updateNotNll(saveDTO);
        return ResponseEntity.ok(ApiResult.success(saveDTO));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除")
    public ResponseEntity<ApiResult<Void>> deleteById(@PathVariable("id") String id) {
        this.service.deleteById(id);
        return ResponseEntity.ok(ApiResult.success(null));
    }

    /**
     * 批量删除
     *
     * @param ids 主键列表
     * @return 删除数量
     */
    @DeleteMapping
    @Operation(summary = "批量删除")
    public ResponseEntity<ApiResult<Integer>> deleteByIds(List<String> ids) {
        this.service.deleteByIds(ids);
        return ResponseEntity.ok(ApiResult.success(ids.size()));
    }
}


