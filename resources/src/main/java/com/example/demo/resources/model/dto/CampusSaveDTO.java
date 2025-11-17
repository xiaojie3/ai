package com.example.demo.resources.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class CampusSaveDTO {
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
}
