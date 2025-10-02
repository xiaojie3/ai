package com.example.edu.common;


import io.micrometer.common.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

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
            Class<?> sourceClass = source.getClass();
            Field[] fields = sourceClass.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true); // 允许访问私有字段
                String fieldName = field.getName();
                Object value = field.get(source);


                Field targetField = target.getClass().getField(fieldName);
                targetField.setAccessible(true);
                targetField.set(target, value);

            }
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
            throw new IllegalArgumentException("Source object and target class cannot be null.");
        }

        try {
            // 2. 创建目标对象的新实例
            // 这里假设目标类有一个无参构造函数
            Constructor<T> constructor = targetClass.getDeclaredConstructor();
            constructor.setAccessible(true); // 如果无参构造是私有的，也能访问
            T targetInstance = constructor.newInstance();

            // 3. 获取源对象的所有声明字段（包括 private）
            Field[] sourceFields = source.getClass().getDeclaredFields();

            // 4. 遍历源对象的字段，并复制到目标实例中
            for (Field sourceField : sourceFields) {
                sourceField.setAccessible(true); // 允许访问私有字段
                String fieldName = sourceField.getName();
                Object fieldValue = sourceField.get(source);

                // 尝试在目标对象中找到同名的字段
                try {
                    Field targetField = targetClass.getDeclaredField(fieldName);
                    targetField.setAccessible(true);
                    targetField.set(targetInstance, fieldValue);
                } catch (NoSuchFieldException e) {
                    // 如果目标对象没有这个字段，则跳过，继续复制下一个字段
                    // 你也可以选择在这里抛出异常，强制要求字段完全匹配
                    // throw new RuntimeException("Target class " + targetClass.getName() + " does not have a field named '" + fieldName + "'.", e);
                }
            }

            // 5. 返回创建好的目标对象实例
            return targetInstance;

        } catch (Exception e) {
            // 将所有反射相关的受检异常包装成运行时异常，方便调用者使用
            throw new RuntimeException("Failed to copy properties from source to target instance.", e);
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
            Class<?> sourceClass = source.getClass();
            Field[] fields = sourceClass.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true); // 允许访问私有字段
                String fieldName = field.getName();
                Object value = field.get(source);

                // 如果字段是 String 类型，并且值为 null 或空白，则跳过
                if (field.getType().equals(String.class) && StringUtils.isBlank((String) value)) {
                    continue;
                }

                // 如果字段不是 String 类型，或者值不是空白，则复制
                Field targetField = target.getClass().getDeclaredField(fieldName);
                targetField.setAccessible(true);
                targetField.set(target, value);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public static <T> T copyPropertiesIgnoreEmpty(Object source, Class<T> targetClass) {
        // 1. 参数校验
        if (source == null || targetClass == null) {
            throw new IllegalArgumentException("Source object and target class cannot be null.");
        }

        try {
            // 2. 创建目标对象的新实例
            // 这里假设目标类有一个无参构造函数
            Constructor<T> constructor = targetClass.getDeclaredConstructor();
            constructor.setAccessible(true); // 如果无参构造是私有的，也能访问
            T targetInstance = constructor.newInstance();

            // 3. 获取源对象的所有声明字段（包括 private）
            Field[] sourceFields = source.getClass().getDeclaredFields();

            // 4. 遍历源对象的字段，并复制到目标实例中
            for (Field sourceField : sourceFields) {
                sourceField.setAccessible(true); // 允许访问私有字段
                String fieldName = sourceField.getName();
                Object fieldValue = sourceField.get(source);
                // 如果字段是 String 类型，并且值为 null 或空白，则跳过
                if (sourceField.getType().equals(String.class) && StringUtils.isBlank((String) fieldValue)) {
                    continue;
                }
                // 尝试在目标对象中找到同名的字段
                try {
                    Field targetField = targetClass.getDeclaredField(fieldName);
                    targetField.setAccessible(true);
                    targetField.set(targetInstance, fieldValue);
                } catch (NoSuchFieldException e) {
                    // 如果目标对象没有这个字段，则跳过，继续复制下一个字段
                    // 你也可以选择在这里抛出异常，强制要求字段完全匹配
                    // throw new RuntimeException("Target class " + targetClass.getName() + " does not have a field named '" + fieldName + "'.", e);
                }
            }

            // 5. 返回创建好的目标对象实例
            return targetInstance;

        } catch (Exception e) {
            // 将所有反射相关的受检异常包装成运行时异常，方便调用者使用
            throw new RuntimeException("Failed to copy properties from source to target instance.", e);
        }
    }
}
