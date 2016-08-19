package com.example.designPatterns.singletonModel;

/**
 * 懒汉单例模式
 */
public class SingletonModel {
    private volatile static SingletonModel instance;

    private SingletonModel(){}

    private SingletonModel getInstance(){
        if (instance == null){
            synchronized (SingletonModel.class){
                if (instance == null){
                    instance = new SingletonModel();
                }
            }
        }
        return instance;
    }
}
