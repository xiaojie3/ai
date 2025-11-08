package com.example.ai.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysUserDTO {
    private String id;

    // 账号
    private String account;

    // 名称
    private String username;

    // 密码
    private String password;

    // 邮箱
    private String email;

    // 性别码
    private String genderCode;

    // 手机号码
    private String phone;

    // 状态码
    private String userStatusCode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private String createBy;

    private String updateBy;
}
