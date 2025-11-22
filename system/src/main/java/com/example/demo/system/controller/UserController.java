package com.example.demo.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.system.model.dto.UserDTO;
import com.example.demo.system.model.dto.UserQueryDTO;
import com.example.demo.system.model.dto.UserSaveDTO;
import com.example.demo.system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
@Tag(name = "用户信息表")
public class UserController {

    private final UserService service;

    /**
     * 分页查询
     *
     * @param queryDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("/query")
    @Operation(summary = "分页查询")
    public IPage<UserDTO> queryByPage(@RequestBody UserQueryDTO queryDTO) {
        return service.queryByPage(queryDTO);
    }

    /**
     * 列表查询
     *
     * @param queryDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("/list")
    @Operation(summary = "列表查询")
    public List<UserDTO> queryByList(UserQueryDTO queryDTO) {
        return null;
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("/find")
    @Operation(summary = "ID查询")
    public UserDTO queryById(String id) {
        return null;
    }

    /**
     * 新增数据
     *
     * @param saveDTO 实体
     * @return 新增结果
     */
    @PostMapping
    @Operation(summary = "新增")
    public UserSaveDTO add(@RequestBody UserSaveDTO saveDTO) {
        return saveDTO;
    }

    /**
     * 编辑数据
     *
     * @param saveDTO 实体
     * @return 编辑结果
     */
    @PostMapping("/edit")
    @Operation(summary = "编辑")
    public UserSaveDTO edit(@RequestBody UserSaveDTO saveDTO) {
        return saveDTO;
    }

    /**
     * 编辑数据
     *
     * @param saveDTO 实体
     * @return 编辑结果
     */
    @PostMapping("/batchEdit")
    @Operation(summary = "批量编辑")
    public UserSaveDTO editByNotNull(@RequestBody UserSaveDTO saveDTO) {
        return saveDTO;
    }

    /**
     * 删除数据
     *
     * @param id 主键
     */
    @PostMapping("/delete")
    @Operation(summary = "删除")
    public void deleteById(String id) {
    }

    /**
     * 批量删除
     *
     * @param ids 主键列表
     * @return 删除数量
     */
    @PostMapping("/batchDelete")
    @Operation(summary = "批量删除")
    public Integer deleteByIds(List<String> ids) {
        return ids.size();
    }
}


