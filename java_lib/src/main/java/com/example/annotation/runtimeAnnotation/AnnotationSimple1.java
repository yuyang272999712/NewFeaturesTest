package com.example.annotation.runtimeAnnotation;

/**
 * 运行时注解使用
 */
public class AnnotationSimple1 {
    public static void main(String[] args){
        Person person = new Person(321, "测试类");
        Class clazz = person.getClass();
        ClassInfo methodInfo = (ClassInfo) clazz.getAnnotation(ClassInfo.class);
        System.out.println("class author："+methodInfo.author());
        System.out.println("class create date："+methodInfo.date());
        System.out.println("class version："+methodInfo.version());
    }
}
