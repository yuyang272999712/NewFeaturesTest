package com.example.reflection;

import java.lang.reflect.Field;

/**
 * 反射获取类中的属性
 */
public class ReflectionSimple3 {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Student student = new Student(24, "于洋");

        Class clazz = student.getClass();

        /**
         * 获取该 Class 对象中的所有属性(不包含从父类继承的属性)
         */
        Field[] fields1 = clazz.getDeclaredFields();
        for (Field field : fields1) {
            System.out.println("declared field name : " + field.getName());
        }
        System.out.println("---------------------------------------");

        /**
         * 获取 Class 对象中指定属性名的属性
         */
        // 获取属性值
        Field field1 = clazz.getDeclaredField("mGrade");
        System.out.println("班级: " + field1.getInt(student));
        /**
         * 修改该属性的值
         */
        field1.set(student, 27);
        System.out.println("班级: " + field1.getInt(student));
        System.out.println("---------------------------------------");

        /**
         * 获取该 Class 对象中的所有public属性(包含从父类继承的属性)
         */
        Field[] fields2 = clazz.getFields();
        for (Field field : fields2) {
            System.out.println("all public field name : " + field.getName());
        }
        System.out.println("---------------------------------------");

        Field field2 = clazz.getField("mName");
        System.out.println("姓名: " + field2.get(student).toString());
    }
}
