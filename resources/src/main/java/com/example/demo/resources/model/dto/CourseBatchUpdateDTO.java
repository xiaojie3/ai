package com.example.demo.resources.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CourseBatchUpdateDTO {
    private List<String> ids;
    // 课程号
    private String courseCode;
    // 课程名称
    private String courseName;
    // 英文名称
    private String englishName;
    // 学分
    private Double credit;
    // 总学时（理论+实践）
    private Integer totalHours;
    // 理论学时
    private Integer theoryHours;
    // 实践学时
    private Integer practiceHours;
    // 课程类型字典码
    private String courseTypeCode;
    // 上课方式字典码
    private String teachingModeCode;
    // 考核方式字典码
    private String assessmentTypeCode;
    // 课程描述
    private String description;
    // ${column.comment}
    private String chineseName;
}
