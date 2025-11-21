package com.example.demo.system.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.system.model.enums.Gender;
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
    @TableId(type = IdType.ASSIGN_UUID)
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
    private Gender gender;

    // 手机号码
    private String phone;

    // 状态码
    private UserStatus status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String createBy;

    private String updateBy;
}
