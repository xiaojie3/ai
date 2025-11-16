package com.example.demo.auth.model.dto;

import lombok.Data;

import java.util.List;
@Data
public class UserInfoDTO {
    private String id;
    private String name;
    List<String> roles;
    List<String> menus;
    String email;
}
