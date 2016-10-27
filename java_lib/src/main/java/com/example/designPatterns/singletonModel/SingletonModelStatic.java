package com.example.designPatterns.singletonModel;

/**
 * !--yuyang 推荐使用这种模式
 * 静态内部类单例模式
 *  当第一次加载这个类时并不会初始化sInstance，只有在第一次调用类的getInstance()方法时才会导致sInstance被初始化。
 *  因此，第一次调用getInstance方法会导致虚拟机加载SingletonHolder类，这种方式不仅能够确保线程安全，也能够保证
 *  单例对象的唯一性，同时也延迟了单例的实例化。
 */

public class SingletonModelStatic {
    private SingletonModelStatic(){}

    public static SingletonModelStatic getInstance(){
        return SingletonHolder.sInstance;
    }

    /**
     * 静态内部类
     */
    private static class SingletonHolder{
        private static final SingletonModelStatic sInstance = new SingletonModelStatic();
    }

}
