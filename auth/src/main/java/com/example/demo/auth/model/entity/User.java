package com.example.demo.auth.model.entity;

import lombok.Data;

@Data
public class User {
    private String id;
    private String account;
    private String password;

    public boolean isEnabled() {
        return true;
    }
}
