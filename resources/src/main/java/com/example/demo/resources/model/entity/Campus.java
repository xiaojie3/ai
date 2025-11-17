package com.example.demo.resources.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 校区表
 *
 * @author robot
 * @since 2025-11-17 10:39:41
 */
@Entity
@Table(name = "res_campus")
@Data
public class Campus {
    @Id
    @Column(length = 64)
    private String id;

    // ${column.comment}
    @Column(name = "campus_code")
    private String campusCode;

    // ${column.comment}
    @Column(name = "school_id")
    private String schoolId;

    // ${column.comment}
    @Column(name = "campus_name")
    private String campusName;

    // ${column.comment}
    @Column(name = "english_name")
    private String englishName;

    // ${column.comment}
    @Column(name = "address")
    private String address;

    // ${column.comment}
    @Column(name = "principal")
    private String principal;

    // ${column.comment}
    @Column(name = "landline")
    private String landline;

    // ${column.comment}
    @Column(name = "phone")
    private String phone;

    // ${column.comment}
    @Column(name = "email")
    private String email;

    // ${column.comment}
    @Column(name = "intro")
    private String intro;

    @Column(name = "create_time", nullable = false, updatable = false) // 不可更新
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    @Column(name = "create_by", length = 64, updatable = false)
    private String createBy;

    @Column(name = "update_by", length = 64)
    private String updateBy;
}
