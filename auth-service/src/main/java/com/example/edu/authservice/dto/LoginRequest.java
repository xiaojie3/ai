package com.example.edu.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "账号不能为空")
    private String account;  // 登录用户名

    @NotBlank(message = "密码不能为空")
    private String password;  // 登录密码
}
