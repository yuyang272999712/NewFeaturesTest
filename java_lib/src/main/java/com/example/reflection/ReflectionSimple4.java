package com.example.reflection;

/**
 * 获取Class的父类或接口
 */
public class ReflectionSimple4 {
    public static void main(String[] args){
        Student student = new Student(27, "于洋");

        Class clazz = student.getClass();

        Class parentClazz = clazz.getSuperclass();
        while (parentClazz != null) {
            System.out.println(clazz.getName()+"的父类是" + parentClazz.getName());
            // 再获取父类的上一层父类，直到最后的 Object 类，Object 的父类为 null
            parentClazz = parentClazz.getSuperclass();
        }
        System.out.println("------------------------------");

        Class[] clazzs = clazz.getInterfaces();
        System.out.println(clazz.getName()+"实现的接口有：");
        for (Class _clazz:clazzs){
            System.out.println("接口："+_clazz.getName());
        }
    }
}
