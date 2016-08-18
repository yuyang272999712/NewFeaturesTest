package com.example.designPatterns.observerModel;

/**
 * 观察者2
 */
public class CitizensObserver2 implements IObserver {
    @Override
    public void update(String msg) {
        System.out.println("CitizensObserver2 在家获取天气信息 -->" + msg);
    }
}
