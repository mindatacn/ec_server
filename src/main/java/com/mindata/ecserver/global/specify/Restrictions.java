package com.mindata.ecserver.global.specify;

import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * 条件构造器
 * 用于创建条件表达式
 *
 * @Class Name Restrictions
 * @Author lee
 */
public class Restrictions {

    /**
     * 等于
     */
    public static SimpleExpression eq(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && StringUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Criterion.Operator.EQ);
    }

    /**
     * 不等于
     */
    public static SimpleExpression ne(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && StringUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Criterion.Operator.NE);
    }

    /**
     * 模糊匹配
     */
    public static SimpleExpression like(String fieldName, String value, boolean ignoreNull) {
        if (ignoreNull && StringUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Criterion.Operator.LIKE);
    }

    /**
     */
//    public static SimpleExpression like(String fieldName, String value,
//                                        MatchMode matchMode, boolean ignoreNull) {
//        if (StringUtils.isEmpty(value)) return null;
//        return null;
//    }

    /**
     * 大于
     */
    public static SimpleExpression gt(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && StringUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Criterion.Operator.GT);
    }

    /**
     * 小于
     */
    public static SimpleExpression lt(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && StringUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Criterion.Operator.LT);
    }

    /**
     * 小于等于
     */
    public static SimpleExpression lte(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && StringUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Criterion.Operator.GTE);
    }

    /**
     * 大于等于
     */
    public static SimpleExpression gte(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && StringUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Criterion.Operator.LTE);
    }

    /**
     * 并且
     */
    public static LogicalExpression and(Criterion... criterions) {
        return new LogicalExpression(criterions, Criterion.Operator.AND);
    }

    /**
     * 或者
     */
    public static LogicalExpression or(Criterion... criterions) {
        return new LogicalExpression(criterions, Criterion.Operator.OR);
    }

    /**
     * 包含于
     */
    @SuppressWarnings("rawtypes")
    public static LogicalExpression in(String fieldName, Collection value, boolean ignoreNull) {
        if (ignoreNull && (value == null || value.isEmpty())) {
            return null;
        }
        SimpleExpression[] ses = new SimpleExpression[value.size()];
        int i = 0;
        for (Object obj : value) {
            ses[i] = new SimpleExpression(fieldName, obj, Criterion.Operator.EQ);
            i++;
        }
        return new LogicalExpression(ses, Criterion.Operator.OR);
    }
}