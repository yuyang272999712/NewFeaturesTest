package com.example.designPatterns.observerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 天气主题－被观察者
 */
public class WeatherSubject implements ISubject {
    private List<IObserver> observers = new ArrayList<IObserver>();

    /**
     * 天气信息
     */
    private String weatherMsg;

    /**
     * 主题更新消息
     *
     * @param msg
     */
    public void setMsg(String msg) {
        this.weatherMsg = msg;
        notifyObservers();
    }

    @Override
    public void registerObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update(weatherMsg);
        }
    }
}
