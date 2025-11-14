package com.example.demo.common.util;

import io.micrometer.common.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MyUtils {

    /**
     * 驼峰转下划线（如 "userId" -> "user_id"）
     * 新增功能：如果当前字符是数字，且前一个字符不是数字，也添加下划线（如 "user2Name" -> "user_2_name"）
     *
     * @param camelCase 待转换的驼峰式字符串
     * @return 转换后的下划线式字符串
     */
    public static String toUnderScoreCase(String camelCase) {
        // 1. 处理空字符串或 null 的情况
        if (camelCase == null || camelCase.isEmpty()) {
            return "";
        }

        // 2. 初始化一个 StringBuilder，用于高效拼接字符
        StringBuilder sb = new StringBuilder();
        // 将第一个字符直接追加到结果中（无论其是字母还是数字）
        sb.append(camelCase.charAt(0));

        // 3. 从第二个字符开始遍历输入字符串
        for (int i = 1; i < camelCase.length(); i++) {
            char currentChar = camelCase.charAt(i);
            char prevChar = camelCase.charAt(i - 1); // 获取前一个字符

            // 4. 核心判断逻辑：
            //    a. 如果当前字符是大写字母，需要添加下划线
            //    b. 如果当前字符是数字，并且前一个字符不是数字，也需要添加下划线
            if (Character.isUpperCase(currentChar) ||
                    (Character.isDigit(currentChar) && !Character.isDigit(prevChar))) {

                sb.append("_"); // 添加下划线
                // 将当前字符转换为小写后追加（数字转换为小写后不变）
                sb.append(Character.toLowerCase(currentChar));
            } else {
                // 5. 如果不满足上述条件，直接追加当前字符
                sb.append(currentChar);
            }
        }

        // 6. 将 StringBuilder 转换为最终的字符串并返回
        return sb.toString();
    }

    /**
     * 复制源对象的属性到目标对象。
     *
     * @param source      源对象
     * @param targetClass 目标对象
     */
    public static <T> T copyObject(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        T target = null;
        try {
            // 创建targetClass的实例（需确保有可访问的无参构造方法）
            target = targetClass.getDeclaredConstructor().newInstance();
            copyObject(source, target, true);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return target;
    }

    public static <T> T copyObjectNotNull(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        T target = null;
        try {
            // 创建targetClass的实例（需确保有可访问的无参构造方法）
            target = targetClass.getDeclaredConstructor().newInstance();
            copyObject(source, target, false);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return target;
    }

    public static void copyObject(Object source, Object target, Boolean copyNull) {
        try {
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
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    /**
     * 递归地获取一个类及其所有父类的所有字段。
     *
     * @param clazz 要获取字段的类
     * @return 一个包含所有字段的 List
     */
    public static List<Field> getAllFields(Class<?> clazz) {
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
    public static Field findFieldByName(List<Field> fields, String fieldName) {
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
