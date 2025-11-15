package com.example.demo.system.controller;

import com.example.demo.common.model.ApiResult;
import com.example.demo.common.model.PageResult;
import com.example.demo.system.model.dto.SysRoleDTO;
import com.example.demo.system.model.dto.SysRoleQueryDTO;
import com.example.demo.system.model.dto.SysRoleSaveDTO;
import com.example.demo.system.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/sysRole")
@RequiredArgsConstructor
@Tag(name = "角色表")
public class SysRoleController {

    private final SysRoleService service;

    /**
     * 分页查询
     *
     * @param queryDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("/query")
    @Operation(summary = "分页查询")
    public ResponseEntity<ApiResult<PageResult<SysRoleDTO>>> queryByPage(@RequestBody SysRoleQueryDTO queryDTO) {
        return ResponseEntity.ok(ApiResult.success(this.service.queryByPage(queryDTO)));
    }

    /**
     * 列表查询
     *
     * @param queryDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("list")
    @Operation(summary = "列表查询")
    public ResponseEntity<ApiResult<List<SysRoleDTO>>> queryByList(SysRoleQueryDTO queryDTO) {
        return ResponseEntity.ok(ApiResult.success(this.service.list(queryDTO)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("/find")
    @Operation(summary = "ID查询")
    public ResponseEntity<ApiResult<SysRoleDTO>> queryById(String id) {
        return ResponseEntity.ok(ApiResult.success(this.service.FindById(id)));
    }

    /**
     * 新增数据
     *
     * @param saveDTO 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    @Operation(summary = "新增")
    public ResponseEntity<ApiResult<SysRoleSaveDTO>> add(SysRoleSaveDTO saveDTO) {
        this.service.save(saveDTO);
        return ResponseEntity.ok(ApiResult.success(saveDTO));
    }

    /**
     * 编辑数据
     *
     * @param saveDTO 实体
     * @return 编辑结果
     */
    @PostMapping("/edit")
    @Operation(summary = "编辑")
    public ResponseEntity<ApiResult<SysRoleSaveDTO>> edit(SysRoleSaveDTO saveDTO) {
        this.service.update(saveDTO);
        return ResponseEntity.ok(ApiResult.success(saveDTO));
    }

    /**
     * 批量修改
     *
     * @param saveDTO 实体
     * @return 编辑结果
     */
    @PostMapping("/batchEdit")
    @Operation(summary = "批量修改")
    public ResponseEntity<ApiResult<SysRoleSaveDTO>> editByNotNull(SysRoleSaveDTO saveDTO) {
        this.service.updateNotNll(saveDTO);
        return ResponseEntity.ok(ApiResult.success(saveDTO));
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
        return ResponseEntity.ok(ApiResult.success(null));
    }

    /**
     * 批量删除
     *
     * @param ids 主键列表
     * @return 删除数量
     */
    @PostMapping("/batchDelete")
    @Operation(summary = "批量删除")
    public ResponseEntity<ApiResult<Integer>> deleteByIds(List<String> ids) {
        this.service.deleteByIds(ids);
        return ResponseEntity.ok(ApiResult.success(ids.size()));
    }
}


