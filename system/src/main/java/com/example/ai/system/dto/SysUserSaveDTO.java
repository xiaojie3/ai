package com.example.ai.system.dto;

import lombok.Data;

@Data
public class SysUserSaveDTO  {
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
}
