package com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主要用于给OnClick这类注解上添加注解，毕竟事件很多，并且设置监听器的名称，监听器的类型，调用的方法名都是固定的，对应OnClick中代码的：
 * listenerType = View.OnClickListener.class, listenerSetter = "setOnClickListener", methodName = "onClick"
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    Class<?> listenerType();
    String listenerSetter();
    String methodName();
}
