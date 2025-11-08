package com.example.ai.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 通用分页响应结果类（泛型）
 * 用途：所有分页接口的统一返回格式，适配任意业务数据类型
 * @param <T> 业务数据类型（如 SysUserDTO、OrderDTO 等）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    /** 当前页数据列表（核心：业务数据） */
    private List<T> records;

    /** 总记录数（前端用于计算分页控件） */
    private Long total;

    /** 当前页码（与请求参数一致，便于前端回显） */
    private Integer current;

    /** 每页条数（与请求参数一致，便于前端回显） */
    private Integer pageSize;

    /** 总页数（自动计算，前端用于渲染分页页码） */
    private Integer totalPages;

    /**
     * 构造方法：自动计算总页数
     * @param records 当前页数据
     * @param total 总记录数
     * @param current 当前页码
     * @param pageSize 每页条数
     */
    public PageResult(List<T> records, Integer current, Integer pageSize, Long total) {
        this.records = records;
        this.total = total;
        this.current = current;
        this.pageSize = pageSize;
        // 计算总页数：向上取整（如 total=21，pageSize=10 → 总页数=3）
        this.totalPages = (int) Math.ceil((double) total / pageSize);
    }
}