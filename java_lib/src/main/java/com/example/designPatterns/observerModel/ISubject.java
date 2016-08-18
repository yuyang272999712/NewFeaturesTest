package com.example.designPatterns.observerModel;

/**
 * 抽象主题
 *  所有的主题必须实现此接口
 */
public interface ISubject {
    /**
     * 注册一个观察着
     *
     * @param observer
     */
    public void registerObserver(IObserver observer);

    /**
     * 移除一个观察者
     *
     * @param observer
     */
    public void removeObserver(IObserver observer);

    /**
     * 通知所有的观察着
     */
    public void notifyObservers();
}
