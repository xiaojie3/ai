package com.example.demo.common.model;

import com.example.demo.common.annotation.FieldMapping;
import com.example.demo.common.annotation.OperatorMapping;
import com.example.demo.common.util.MyUtils;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseJooqRepository {

    protected DSLContext dsl;

    @Autowired
    public void setDsl(DSLContext dsl) {
        this.dsl = dsl;
    }

    protected abstract SelectJoinStep<?> createFrom(SelectSelectStep<?> sqlSelect);

    protected abstract Table<?> mainTable();

    /**
     * 分页查询
     * @return 分页结果
     */
    protected <T, QueryDTO extends PageParams> PageResult<T> queryByPage(QueryDTO queryDTO, Class<T> dtoClass) {
        // 1. 构建基础查询（可添加条件）
        List<Field<?>> fields = getSelectFields(dtoClass);
        var baseQuery = where(queryDTO,createFrom(dsl.select(fields)));
        // 2. 查询总记录数（注意：排除 limit/offset，否则总数不准确）
        long total = baseQuery.fetch().size();

        // 3. 计算偏移量，执行分页查询
        long offset = queryDTO.getOffset();
        List<T> result = baseQuery
                .limit(queryDTO.getSize())  // 限制每页条数
                .offset(offset)   // 跳过前面的记录
                .fetchInto(dtoClass);         // 执行查询

        // 4. 封装分页结果
        return new PageResult<>(result, queryDTO.getCurrent(), queryDTO.getSize(), total);
    }

    protected <QueryDTO> long count(QueryDTO queryDTO) {
        var baseQuery = where(queryDTO,createFrom(dsl.select()));
        return baseQuery.fetch().size();
    }

    protected <T> List<T> list(Class<T> dtoClass) {
        List<Field<?>> fields = getSelectFields(dtoClass);
        return createFrom(dsl.select(fields)).fetchInto(dtoClass);
    }

    protected <T, QueryDTO> List<T> list(QueryDTO queryDTO, Class<T> dtoClass) {
        List<Field<?>> fields = getSelectFields(dtoClass);
        return where(queryDTO,createFrom(dsl.select(fields))).fetchInto(dtoClass);
    }
    /**
     * 从DTO提取字段并映射到jOOQ表字段（带校验）
     *
     * @param dtoClass 查询DTO类
     * @return 匹配的jOOQ字段列表
     */
    protected List<Field<?>> getSelectFields(Class<?> dtoClass) {
        List<Field<?>> resultFields = new ArrayList<>();
        java.lang.reflect.Field[] dtoFields = dtoClass.getDeclaredFields();
        for (java.lang.reflect.Field dtoField : dtoFields) {
            // 添加到结果列表
            Field<?> field = addField(dtoField, "select");
            if(null != field) {
                resultFields.add(field);
            }
        }
        return resultFields;
    }

    /**
     * 添加查询字段
     */
    private Field<?> addField(java.lang.reflect.Field dtoField,String type) {
        String fieldName = MyUtils.toUnderScoreCase(dtoField.getName()); // DTO字段名（如"userId"、"deptName"）
        if("limit".equals(fieldName)) {
            return null;
        }
        Table<?> targetTable;
        String columnName;

        SelectJoinStep<?> joinStep = createFrom(dsl.select());
        // 1. 解析字段的来源表（通过注解或默认主表）
        FieldMapping fieldMapping = dtoField.getAnnotation(FieldMapping.class);
        if (fieldMapping != null) {
            // 有注解：使用注解指定的表别名和字段
            String[] s = fieldMapping.value().split("\\.");
            if (s.length == 2) {
                targetTable = joinStep.asTable(s[0].toUpperCase());
                columnName = s[1];
            } else if (s.length == 1) {
                columnName = s[0];
                targetTable = mainTable();
            } else {
                throw new IllegalArgumentException("格式错误，正确格式应该是：字段或者表别名.字段，例如：user_id或者sys_user.user_id");
            }
        } else {
            // 无注解：默认主表
            targetTable = mainTable();
            // 转换DTO字段名为表字段名（驼峰→下划线）
            columnName = fieldName; // 如"userId"→"user_id"
        }

        // 2. 校验字段是否在表中存在
        Field<?> jooqField = targetTable.field(columnName);
        if (jooqField == null) {
            throw new IllegalArgumentException(
                    "表" + targetTable.getName() + "中未找到字段：" + columnName + "（DTO字段：" + fieldName + "）"
            );
        }
        // 3. 如果Dto参数和列名不一致，则需要起别名
        if(fieldName.equals(columnName)) {
            return jooqField;
        }
        return "select".equals(type) ? jooqField.as(fieldName) : jooqField;
    }

    protected <QueryDTO> SelectConditionStep<?> where(QueryDTO queryDTO, SelectJoinStep<?> sql) {
        java.lang.reflect.Field[] fields = queryDTO.getClass().getDeclaredFields();
        SelectConditionStep<?> sqlWhere = sql.where();
        for (java.lang.reflect.Field dtoField : fields) {
            dtoField.setAccessible(true);
            try {
                Field<?> jooqField = addField(dtoField, "where");
                if(null == jooqField) {
                    continue;
                }
                Class<?> targetType = jooqField.getType();
                // 初始化类型转换服务（以Spring的ConversionService为例，更通用）
                ConversionService conversionService = new DefaultConversionService();
                // 关键：将 Field<?> 强转为 Field<T>，并使用转换后的值
                @SuppressWarnings("unchecked") // 抑制未检查的转换警告（手动确保类型安全）
                Field<Object> typedField = (Field<Object>) jooqField;

                Object fieldValue = conversionService.convert(dtoField.get(queryDTO), targetType);
                if(null == fieldValue || "".equals(fieldValue)) {
                    continue;
                }
                OperatorMapping whereMapping = dtoField.getAnnotation(OperatorMapping.class);
                if (null != whereMapping) {
                    switch (whereMapping.value()) {
                        case NE -> sqlWhere = sqlWhere.and(typedField.ne(fieldValue));
                        case GT -> sqlWhere = sqlWhere.and(typedField.gt(fieldValue));
                        case GE -> sqlWhere = sqlWhere.and(typedField.ge(fieldValue));
                        case LT -> sqlWhere = sqlWhere.and(typedField.lt(fieldValue));
                        case LE -> sqlWhere = sqlWhere.and(typedField.le(fieldValue));
                        case IN -> sqlWhere = sqlWhere.and(typedField.in(fieldValue));
                        case LIKE -> sqlWhere = sqlWhere.and(typedField.like(String.valueOf(fieldValue)));
                        case NO_LIKE -> sqlWhere = sqlWhere.and(typedField.notLike(String.valueOf(fieldValue)));
                        case IS_NULL -> sqlWhere = sqlWhere.and(typedField.isNull());
                        case IS_NOT_NULL -> sqlWhere = sqlWhere.and(typedField.isNotNull());
                        case BETWEEN -> sqlWhere = betWeen(sqlWhere,typedField,fieldValue,true);
                        case NOT_BETWEEN -> sqlWhere = betWeen(sqlWhere,typedField,fieldValue,false);
                    }
                }
                // 默认为等于
                sqlWhere = sqlWhere.and(typedField.eq(fieldValue));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return sqlWhere;
    }

    private SelectConditionStep<?> betWeen(SelectConditionStep<?> sqlWhere, Field<Object> typedField, Object fieldValue, boolean b) {
        // 校验 fieldValue 是否为 List 且包含两个元素
        if (!(fieldValue instanceof List<?> valueList) || ((List<?>) fieldValue).size() != 2) {
            throw new IllegalArgumentException("BETWEEN 条件需要包含两个元素的 List，当前值：" + fieldValue);
        }
        // 转换为 List 并获取开始值和结束值
        Object startValue = valueList.get(0);
        Object endValue = valueList.get(1);
        if (b) {
            return sqlWhere.and(typedField.between(startValue, endValue));
        }
        return sqlWhere.and(typedField.notBetween(startValue, endValue));
    }
}
