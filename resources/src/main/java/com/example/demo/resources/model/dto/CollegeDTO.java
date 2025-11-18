package com.example.demo.resources.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class CollegeDTO {
    private String id;

    // ${column.comment}
    private String collegeCode;

    // ${column.comment}
    private String campusId;

    // ${column.comment}
    private String collegeName;

    // ${column.comment}
    private String collegeAbbr;

    // ${column.comment}
    private String englishName;

    // ${column.comment}
    private String dean;

    // ${column.comment}
    private String landline;

    // ${column.comment}
    private String phone;

    // ${column.comment}
    private String email;

    // ${column.comment}
    private String intro;

    // ${column.comment}
    private String campusName;

    // ${column.comment}
    private String chineseAbbr;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private String createBy;

    private String updateBy;
}
