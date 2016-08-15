package com.example.annotation.classAnnotation;

/**
 * 模拟ButterKnife
 */
public class ClassAnnotation {
    public static void doSomething(Object target){
        Class<?> orgClass = target.getClass();
        String clsName = orgClass.getName();
        try {
            Class<?> targetClass = Class.forName(clsName + "$$yuyang");
            CommonInterface<Object> viewBinder = (CommonInterface<Object>) targetClass.newInstance();
            viewBinder.doSomething(target);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public interface CommonInterface<T> {
        void doSomething(T target);
    }
}
