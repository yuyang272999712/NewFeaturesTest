package com.example.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

/**
 * 获取Class对象并获取对象的构造方法
 */
public class ReflectionSimple1 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        /**
         * 获取Class对象
         */
        Class<?> clazz1 = Class.forName("com.example.reflection.Student");
        Class<?> clazz2 = Student.class;

        System.out.println("获取Class对象，clazz1="+clazz1.getName());
        System.out.println("获取Class对象，clazz2="+clazz2.getName());
        System.out.println("-------------------------------------------------");

        /**
         * 获取以参数的公共构造方法
         */
        Constructor<?> constructor1 = clazz1.getConstructor(String.class);
        //值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查,提升反射速度
        constructor1.setAccessible(true);
        System.out.println("获取clazz1的构造方法="+constructor1.getName());
        /**
         * 通过构造方法创建对象
         */
        Object object1 = constructor1.newInstance("yuyang");
        System.out.println("通过构造方法constructor1创建对象："+object1.toString());

        /**
         * 获取所有公共构造方法
         */
        Constructor<?>[] constructor2 = clazz2.getConstructors();
        for (Constructor constructor:constructor2){
            //获取构造方法的参数
            Parameter[] parameters = constructor.getParameters();
            if (parameters.length > 1){
                Object object2 = constructor.newInstance(3, "liming");
                System.out.println("通过构造方法constructor2创建对象："+object2.toString());
            }
        }
    }
}
