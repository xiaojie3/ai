package com.example.demo.system.model.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus implements IEnum<String> {
    ENABLED("1", "启用"),
    DISABLED("0", "禁用");
    private final String value;
    private final String desc;
}
