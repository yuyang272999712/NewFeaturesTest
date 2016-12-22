package com.example.designPatterns.singletonModel;

import java.io.ObjectStreamException;

/**
 * 懒汉单例模式
 *  volatile只适合JDK1.5以后的版本，之前的版本还是没法解决执行顺序的问题
 */
public class SingletonModel {
    private volatile static SingletonModel instance;

    private SingletonModel(){}

    public static SingletonModel getInstance(){
        if (instance == null){
            synchronized (SingletonModel.class){
                if (instance == null){
                    instance = new SingletonModel();
                }
            }
        }
        return instance;
    }

    /**
     * 防止反序列化生成新的对象
     * @return
     * @throws ObjectStreamException
     */
    private Object readResolve() throws ObjectStreamException {
        return instance;
    }
}
