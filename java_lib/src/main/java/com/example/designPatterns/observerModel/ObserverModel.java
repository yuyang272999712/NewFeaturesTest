package com.example.designPatterns.observerModel;

/**
 * 观察者模式
 */
public class ObserverModel {
    public static void main(String[] args){
        WeatherSubject subject = new WeatherSubject();
        CitizensObserver1 observer1 = new CitizensObserver1();
        CitizensObserver2 observer2 = new CitizensObserver2();

        subject.registerObserver(observer1);
        subject.registerObserver(observer2);

        subject.setMsg("今日有大雨！！");
    }
}
