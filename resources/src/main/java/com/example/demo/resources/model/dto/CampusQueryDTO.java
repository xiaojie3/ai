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
public class CampusQueryDTO extends PageParams {
    private String id;

    // ${column.comment}
    private String campusCode;

    // ${column.comment}
    private String schoolId;

    // ${column.comment}
    private String campusName;

    // ${column.comment}
    private String englishName;

    // ${column.comment}
    private String address;

    // ${column.comment}
    private String principal;

    // ${column.comment}
    private String landline;

    // ${column.comment}
    private String phone;

    // ${column.comment}
    private String email;

    // ${column.comment}
    private String intro;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private String createBy;

    private String updateBy;
}
