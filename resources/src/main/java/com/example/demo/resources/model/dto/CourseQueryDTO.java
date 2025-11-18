package com.example.demo.resources.model.dto;

import com.example.demo.common.model.PageParams;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class CourseQueryDTO extends PageParams {
    // 课程ID
    private String id;
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
    // 创建人
    private String createBy;
    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    // 更新人
    private String updateBy;
    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    // ${column.comment}
    private String chineseName;
}
