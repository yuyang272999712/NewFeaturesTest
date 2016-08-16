package com.example;

public class ToastShow {
    public static final String subClassName = "$$yuyang";

    public static void doSomething(Object target){
        Class<?> orgClass = target.getClass();
        String clsName = orgClass.getName();
        try {
            Class<?> targetClass = Class.forName(clsName + subClassName);
            ViewBinder<Object> viewBinder = (ViewBinder<Object>) targetClass.newInstance();
            viewBinder.doSomething(target);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void showToast(Object target) {
        Class<?> orgClass = target.getClass();
        String clsName = orgClass.getName();
        try {
            Class<?> targetClass = Class.forName(clsName + subClassName);
            ViewBinder<Object> viewBinder = (ViewBinder<Object>) targetClass.newInstance();
            viewBinder.showToast(target);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
