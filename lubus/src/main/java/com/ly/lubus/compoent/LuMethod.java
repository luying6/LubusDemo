package com.ly.lubus.compoent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创建人：luying
 * 创建时间：16/11/30
 * 类说明：组件/注解
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LuMethod {
    String uri() default "";
    boolean needSync() default false;
}
