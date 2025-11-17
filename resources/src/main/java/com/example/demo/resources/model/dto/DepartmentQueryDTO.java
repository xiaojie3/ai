package com.example.demo.resources.model.dto;

import java.time.LocalDateTime;

import com.example.demo.common.model.PageParams;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class DepartmentQueryDTO extends PageParams {
    private String id;

    // 学院 ID
    private String collegeId;

    // 部门编号
    private String departmentCode;

    // 部门名称
    private String departmentName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private String createBy;

    private String updateBy;
}
