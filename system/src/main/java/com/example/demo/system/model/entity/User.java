package com.example.demo.system.model.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.system.model.enums.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @author xieJ
 * @since 2025-11-08 23:46:49
 */
@Data
@TableName("`sys_user`")
public class User {
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
    private UserStatus userStatusCode;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String createBy;

    private String updateBy;
}
