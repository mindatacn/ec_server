package com.mindata.ecserver.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对于耗时操作的插入功能加锁
 *
 * @author wuweifeng wrote on 2018/2/9.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LockSlowDealAnnotation {
    String moduleName() default "";
}
