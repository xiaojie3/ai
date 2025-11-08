package com.example.ai.common.util;

import io.micrometer.common.util.StringUtils;
import org.jooq.Table;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class MyUtils {

    /**
     * 扫描Tables类，提取所有静态表对象（表名 → 表对象）
     */
    private static Map<String, Table<?>> scanTables(Class<?> tables) {
        Map<String, Table<?>> map = new HashMap<>();
        try {
            // 获取Tables类的所有静态字段
            Field[] fields = tables.getDeclaredFields();
            for (Field field : fields) {
                // 只处理static且类型为Table的字段
                if (Modifier.isStatic(field.getModifiers()) && Table.class.isAssignableFrom(field.getType())) {
                    String tableName = field.getName(); // 表名（如"GEN_TABLE"、"SYS_DEPT"）
                    Table<?> table = (Table<?>) field.get(null); // 获取静态字段的值（表对象）
                    map.put(tableName, table);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("扫描Tables类失败", e);
        }
        return Collections.unmodifiableMap(map);
    }

    /**
     * 驼峰转下划线（如"userId"→"user_id"）
     */
    public static String toUnderScoreCase(String camelCase) {
        if (camelCase == null || camelCase.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(camelCase.charAt(0));
        for (int i = 1; i < camelCase.length(); i++) {
            char c = camelCase.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_").append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 复制源对象的属性到目标对象。
     *
     * @param source 源对象
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

    public static void copyObject(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        try {
            // 创建targetClass的实例（需确保有可访问的无参构造方法）
            copyObject(source, target, true);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public static void copyObject(Object source, Object target, Boolean copyNull) throws Exception {
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
    public static void copyObjectNotNull(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        try {
            copyObject(source, target, false);
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

    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
