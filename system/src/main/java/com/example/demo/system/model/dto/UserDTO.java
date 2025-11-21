package com.example.demo.system.model.dto;

import com.example.demo.system.model.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private String id;

    // 账号
    private String account;

    // 名称
    private String username;

    // 邮箱
    private String email;

    // 性别码
    private String genderCode;

    // 性别码
    private String genderName;

    // 手机号码
    private String phone;

    // 状态码
    private UserStatus userStatusCode;

    public String userStatusDesc() {
        return userStatusCode.getDesc();
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private String createBy;

    private String updateBy;
}
