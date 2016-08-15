package com.example.annotation.classAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 运行时注解
 */
@Documented
@Target(ElementType.TYPE)//只可用语类的注解
@Retention(RetentionPolicy.CLASS)//运行时注解
@Inherited
public @interface ToastShow {
    String message() default "编译时注解";
}
