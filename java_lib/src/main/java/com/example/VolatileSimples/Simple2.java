package com.example.VolatileSimples;

/**
 * 双重检测－提高单例模式效率
 *  volatile只适合JDK1.5以后的版本，之前的版本还是没法解决执行顺序的问题
 */
public class Simple2 {
    private volatile static Simple2 simple2 = null;

    private Simple2(){
    }

    public static Simple2 getInstance(){
        if (simple2 == null){
            synchronized (Simple2.class){
                if (simple2 == null){
                    simple2 = new Simple2();
                }
            }
        }
        return simple2;
    }
}
