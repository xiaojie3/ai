package com.example.demo.system.model.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum Gender implements IEnum<String> {
    UNKNOWN("0", "未知"),
    MAN("1", "男"),
    WOMAN("2", "女");

    private final String code;
    private final String name;

    @JsonValue
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("code", this.code);
        map.put("name", this.name);
        return map;
    }

    @Override
    public String getValue() {
        return code;
    }
}
