package com.example.demo.resources.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CollegeBatchUpdateDTO {
    private List<String> ids;

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
}
