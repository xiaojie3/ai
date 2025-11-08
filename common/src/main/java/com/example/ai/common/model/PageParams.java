package com.example.ai.common.model;

import lombok.Data;

/**
 * 基础分页父类：抽取分页请求通用参数（页码、页大小）
 * 适用于：所有需要分页的请求参数类继承
 */
@Data
public abstract class PageParams {
    /** 当前页码，默认1（前端不传时取默认值） */
    private Integer current = 1;

    /** 每页条数，默认10（前端可自定义，建议限制最大为100，避免查询压力） */
    private Integer size = 10;

    /**
     * 计算偏移量（供 MyBatis/JOOQ 等 ORM 使用）
     * @return 偏移量（从第几条数据开始查询）
     */
    public Integer getOffset() {
        // 校验参数合法性：避免页码/页大小为负数
        if (current < 1) current = 1;
        if (size < 1) size = 10;
        return (current - 1) * size;
    }
}