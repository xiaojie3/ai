package com.example.demo.resources.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class DepartmentBatchUpdateDTO {
    private List<String> ids;

    // 学院 ID
    private String collegeId;

    // 部门编号
    private String departmentCode;

    // 部门名称
    private String departmentName;
}
