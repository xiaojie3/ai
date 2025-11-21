package com.example.demo.auth.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_user")
public class User {
    private String id;
    private String account;
    private String password;

    public boolean isEnabled() {
        return true;
    }
}
