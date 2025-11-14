package com.example.demo.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // 用于字段上
@Retention(RetentionPolicy.RUNTIME) // 运行时保留，便于反射获取
public @interface FieldMapping {
    String value();
}
