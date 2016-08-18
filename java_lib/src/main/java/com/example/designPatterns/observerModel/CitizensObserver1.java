package com.example.designPatterns.observerModel;

/**
 * 观察者1
 */
public class CitizensObserver1 implements IObserver {
    @Override
    public void update(String msg) {
        System.out.println("CitizensObserver1 上班路上获取天气信息  -->" + msg);
    }
}
