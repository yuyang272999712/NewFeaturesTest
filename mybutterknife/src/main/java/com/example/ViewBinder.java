package com.example;

/**
 * 统一接口，方便反射后获取类的通用模式
 */
public interface ViewBinder<T> {
    void doSomething(T target);

    void showToast(T target);
}
