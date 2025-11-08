package com.example.ai.common.annotation;

import lombok.Getter;

@Getter
public enum OperatorEnum {
    // 枚举常量需传入对应的描述参数
    EQ("等于"),
    NE("不等于"),
    GT("大于"),
    GE("大于等于"),
    LT("小于"),
    LE("小于等于"),
    IN("包含"),
    NOT_IN("不包含"),
    LIKE("不等于"),
    NO_LIKE("模糊匹配"),
    IS_NULL("为空"),
    IS_NOT_NULL("不为空"),
    BETWEEN("介于"),
    NOT_BETWEEN("不介于");

    // getter方法
    private String name;

    // 构造方法（枚举的构造方法必须是private，且只能在枚举内部使用）
    OperatorEnum(String name) {
        this.name = name;
    }

}
