package com.example.demo.resources.controller;

import com.example.demo.common.model.ApiResult;
import com.example.demo.common.model.PageResult;
import com.example.ai.resources.model.dto.*;
import com.example.demo.resources.model.dto.SchoolDTO;
import com.example.demo.resources.model.dto.SchoolQueryDTO;
import com.example.demo.resources.model.dto.SchoolSaveDTO;
import com.example.demo.resources.service.SchoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resource/school")
@RequiredArgsConstructor
@Tag(name = "学校表")
public class SchoolController {

    private final SchoolService service;

    /**
     * 分页查询
     *
     * @param queryDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("/query")
    @Operation(summary = "分页查询")
    public ResponseEntity<ApiResult<PageResult<SchoolDTO>>> queryByPage(@RequestBody SchoolQueryDTO queryDTO) {
        return ResponseEntity.ok(ApiResult.of(this.service.queryByPage(queryDTO)));
    }

    /**
     * 列表查询
     *
     * @param queryDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("list")
    @Operation(summary = "列表查询")
    public ResponseEntity<ApiResult<List<SchoolDTO>>> queryByList(@RequestBody SchoolQueryDTO queryDTO) {
        return ResponseEntity.ok(ApiResult.of(this.service.list(queryDTO)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("/find")
    @Operation(summary = "ID查询")
    public ResponseEntity<ApiResult<SchoolDTO>> queryById(String id) {
        return ResponseEntity.ok(ApiResult.of(this.service.FindById(id)));
    }

    /**
     * 新增数据
     *
     * @param saveDTO 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    @Operation(summary = "新增")
    public ResponseEntity<ApiResult<SchoolSaveDTO>> add(@RequestBody SchoolSaveDTO saveDTO) {
        this.service.save(saveDTO);
        return ResponseEntity.ok(ApiResult.of(saveDTO));
    }

    /**
     * 编辑数据
     *
     * @param saveDTO 实体
     * @return 编辑结果
     */
    @PostMapping("/edit")
    @Operation(summary = "编辑")
    public ResponseEntity<ApiResult<SchoolSaveDTO>> edit(@RequestBody SchoolSaveDTO saveDTO) {
        this.service.update(saveDTO);
        return ResponseEntity.ok(ApiResult.of(saveDTO));
    }

    /**
     * 编辑数据
     *
     * @param saveDTO 实体
     * @return 编辑结果
     */
    @PostMapping("/batchEdit")
    @Operation(summary = "编辑非空参数")
    public ResponseEntity<ApiResult<SchoolSaveDTO>> batchEdit(@RequestBody SchoolSaveDTO saveDTO) {
        this.service.updateNotNll(saveDTO);
        return ResponseEntity.ok(ApiResult.of(saveDTO));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    @Operation(summary = "删除")
    public ResponseEntity<ApiResult<Void>> deleteById(String id) {
        this.service.deleteById(id);
        return ResponseEntity.ok(ApiResult.of(null));
    }

    /**
     * 批量删除
     *
     * @param ids 主键列表
     * @return 删除数量
     */
    @PostMapping("/batchDelete")
    @Operation(summary = "批量删除")
    public ResponseEntity<ApiResult<Integer>> deleteByIds(@RequestBody List<String> ids) {
        this.service.deleteByIds(ids);
        return ResponseEntity.ok(ApiResult.of(ids.size()));
    }
}


