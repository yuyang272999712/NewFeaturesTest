package com.example;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 编译时注解
 */
@Documented
@Target(ElementType.FIELD)//只可用成员变量的注解
@Retention(RetentionPolicy.CLASS)//编译时注解
@Inherited
public @interface MyToastShowAnnotation {
    String message() default "注解的message值";
}
