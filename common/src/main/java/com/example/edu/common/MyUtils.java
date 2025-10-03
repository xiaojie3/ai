package com.example.edu.common;


import io.micrometer.common.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyUtils {
    /**
     * 复制源对象的属性到目标对象。
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        try {
            copy(source, target, true);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    /**
     * 将源对象的属性值复制到一个新创建的目标对象实例中。
     * 此方法会创建一个 targetClass 类型的新实例，并将 source 对象的同名属性值复制过去。
     *
     * @param source      源对象，不能为 null
     * @param targetClass 目标对象的 Class 对象，不能为 null
     * @param <T>         目标对象的类型
     * @return 一个包含了源对象属性值的、新创建的 targetClass 类型的实例。
     * @throws RuntimeException 如果创建目标对象实例或复制属性时发生任何反射异常。
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        // 1. 参数校验
        if (source == null || targetClass == null) {
            return null;
        }
        try {
            // 2. 创建目标对象的新实例
            // 这里假设目标类有一个无参构造函数
            Constructor<T> constructor = targetClass.getDeclaredConstructor();
            constructor.setAccessible(true); // 如果无参构造是私有的，也能访问
            T targetInstance = constructor.newInstance();

            copy(source, targetInstance, true);
            // 5. 返回创建好的目标对象实例
            return targetInstance;

        } catch (Exception e) {
            // 将所有反射相关的受检异常包装成运行时异常，方便调用者使用
            throw new RuntimeException("Failed to copy properties from source to target instance.", e);
        }
    }

    public static void copy(Object source, Object target, Boolean copyNull) throws Exception{
        // 3. 获取源对象的所有声明字段（包括 private）
        List<Field> allSourceFields = getAllFields(source.getClass());
        List<Field> allTargetFields = getAllFields(target.getClass());

        // 4. 遍历源对象的字段，并复制到目标实例中
        for (Field sourceField : allSourceFields) {
            sourceField.setAccessible(true); // 允许访问私有字段
            String fieldName = sourceField.getName();
            Object fieldValue = sourceField.get(source);
            Field targetField = findFieldByName(allTargetFields, fieldName);
            if (targetField == null) {
                // 如果目标对象（包括其所有父类）没有这个字段，则跳过
                continue;
            }
            targetField.setAccessible(true);
            // --- 核心复制逻辑（与之前相同） ---
            if (!copyNull && sourceField.getType().equals(String.class)) {
                if (fieldValue == null || StringUtils.isBlank((String) fieldValue)) {
                    continue;
                }
            }
            targetField.set(target, fieldValue);
        }
    }

    /**
     * 复制源对象的属性到目标对象，但忽略 null、空字符串和纯空白字符串。
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyPropertiesIgnoreEmpty(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        try {
            copy(source, target, false);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public static <T> T copyPropertiesIgnoreEmpty(Object source, Class<T> targetClass) {
        // 1. 参数校验
        if (source == null || targetClass == null) {
            return null;
        }
        try {
            // 2. 创建目标对象的新实例
            // 这里假设目标类有一个无参构造函数
            Constructor<T> constructor = targetClass.getDeclaredConstructor();
            constructor.setAccessible(true); // 如果无参构造是私有的，也能访问
            T targetInstance = constructor.newInstance();
            copy(source, targetInstance, false);
            // 5. 返回创建好的目标对象实例
            return targetInstance;

        } catch (Exception e) {
            // 将所有反射相关的受检异常包装成运行时异常，方便调用者使用
            throw new RuntimeException("Failed to copy properties from source to target instance.", e);
        }
    }

    /**
     * 递归地获取一个类及其所有父类的所有字段。
     *
     * @param clazz 要获取字段的类
     * @return 一个包含所有字段的 List
     */
    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        // 当 clazz 为 null 或达到 Object.class 时停止递归
        while (clazz != null && clazz != Object.class) {
            Field[] declaredFields = clazz.getDeclaredFields();
            Collections.addAll(fields, declaredFields);
            // 移动到父类
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    /**
     * 在字段列表中根据名称查找字段。
     *
     * @param fields    字段列表
     * @param fieldName 要查找的字段名
     * @return Field 对象，如果未找到则返回 null
     */
    private static Field findFieldByName(List<Field> fields, String fieldName) {
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }
}
