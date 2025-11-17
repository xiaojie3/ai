package com.example.demo.resources.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 部门表
 *
 * @author robot
 * @since 2025-11-17 10:57:01
 */
@Entity
@Table(name = "res_department")
@Data
public class Department {
    @Id
    @Column(length = 64)
    private String id;

    // 学院 ID
    @Column(name = "college_id")
    private String collegeId;

    // 部门编号
    @Column(name = "department_code")
    private String departmentCode;

    // 部门名称
    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "create_time", nullable = false, updatable = false) // 不可更新
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    @Column(name = "create_by", length = 64, updatable = false)
    private String createBy;

    @Column(name = "update_by", length = 64)
    private String updateBy;
}
