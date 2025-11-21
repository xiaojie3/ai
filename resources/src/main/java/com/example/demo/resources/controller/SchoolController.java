package com.example.demo.resources.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.resources.model.dto.SchoolDTO;
import com.example.demo.resources.model.dto.SchoolQueryDTO;
import com.example.demo.resources.model.dto.SchoolSaveDTO;
import com.example.demo.resources.model.entity.School;
import com.example.demo.resources.service.SchoolService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 学校表(School)表控制层
 *
 * @author robot
 * @since 2025-11-21 14:05:50
 */
@RestController
@RequestMapping("resources/school")
@RequiredArgsConstructor
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
    public IPage<SchoolDTO> queryByPage(@RequestBody SchoolQueryDTO queryDTO) {
        // 创建分页参数，并指定排序
        Page<School> page = new Page<>(1, 20); // 查第1页，每页20条
        page.addOrder(OrderItem.desc("create_time")); // 按创建时间倒序


        LambdaQueryWrapper<School> wrappers = Wrappers.lambdaQuery();

        if(StringUtils.isNotBlank(queryDTO.getId())) {
            wrappers.eq(School::getId, queryDTO.getId());
        }
        // 执行分页查询
        Page<School> SchoolPage = service.page(page, wrappers);

        return SchoolPage.convert(School -> {
            SchoolDTO dto = new SchoolDTO();
            BeanUtils.copyProperties(School, dto); // 使用 Spring 的工具类
            // 或者用 MapStruct 等更专业的工具
            return dto;
        });
    }

    /**
     * 列表查询
     *
     * @param queryDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("/list")
    @Operation(summary = "列表查询")
    public List<SchoolDTO> queryByList(SchoolQueryDTO queryDTO) {
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
    public SchoolDTO queryById(String id) {
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
    public SchoolSaveDTO add(@RequestBody SchoolSaveDTO saveDTO) {
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
    public SchoolSaveDTO edit(@RequestBody SchoolSaveDTO saveDTO) {
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
    public SchoolSaveDTO editByNotNull(@RequestBody SchoolSaveDTO saveDTO) {
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

