package com.example.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 通过反射获取类的方法对象
 */
public class ReflectionSimple2 {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        Student student = new Student(27, "于洋");

        Class clazz = student.getClass();
        /**
         * 获取Class中的所有方法（不包括父类的）
         */
        Method[] methods1 = clazz.getDeclaredMethods();
        System.out.println("Student类的所有方法：");
        for (Method method : methods1) {
            System.out.println("declared method name : " + method.getName());
            /**
             * 获取方法的属性是否是 private
             */
            System.out.println(method.getName()+"方法是私有的："+ Modifier.isPrivate(method.getModifiers()));
        }
        System.out.println("-------------------------------------------------");

        /**
         * 获取Class中的某个特定方法（可以通过第二个参数区分开重载方法）
         */
        Method method1 = clazz.getDeclaredMethod("learn", String.class);
        /**
         * 获取Method的返回参数类型
         */
        Class returnClass = method1.getReturnType();
        System.out.println("方法 " + method1.getName()+"的返回参数类型是："+returnClass.getName());
        System.out.println("-------------------------------------------------");

        /**
         * 获取Class的所有public方法（包括父类的）
         */
        Method[] methods2 = clazz.getMethods();
        for (Method method:methods2){
            if (method.getName().equals("breathe")){
                /**
                 * 执行Method方法
                 */
                method.invoke(student);
            }
        }
        System.out.println("-------------------------------------------------");

        /**
         * 获取Class中指定的public方法
         */
        Method method2 = clazz.getMethod("takeAnExamination", null);
        method2.invoke(student);

    }
}
