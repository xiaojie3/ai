package com.example.demo.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest  {
    private String account;
    private String password;
}
