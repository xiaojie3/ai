package com.example.demo.resources.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 学院表
 *
 * @author robot
 * @since 2025-11-18 10:31:08
 */
@Entity
@Table(name = "res_college")
@Data
public class College {
    @Id
    @Column(length = 64)
    private String id;

    // ${column.comment}
    @Column(name = "college_code")
    private String collegeCode;

    // ${column.comment}
    @Column(name = "campus_id")
    private String campusId;

    // ${column.comment}
    @Column(name = "college_name")
    private String collegeName;

    // ${column.comment}
    @Column(name = "college_abbr")
    private String collegeAbbr;

    // ${column.comment}
    @Column(name = "english_name")
    private String englishName;

    // ${column.comment}
    @Column(name = "dean")
    private String dean;

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

    // ${column.comment}
    @Column(name = "campus_name")
    private String campusName;

    // ${column.comment}
    @Column(name = "chinese_abbr")
    private String chineseAbbr;

    @Column(name = "create_time", nullable = false, updatable = false) // 不可更新
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    @Column(name = "create_by", length = 64, updatable = false)
    private String createBy;

    @Column(name = "update_by", length = 64)
    private String updateBy;
}
