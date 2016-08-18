package com.example.designPatterns.observerModel;

/**
 * 抽象观察者
 *  所有的观察者需要实现此接口
 */
public interface IObserver {
    public void update(String msg);
}
