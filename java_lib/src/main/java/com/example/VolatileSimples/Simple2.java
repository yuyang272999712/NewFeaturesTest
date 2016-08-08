package com.example.VolatileSimples;

/**
 * 双重检测－提高单例模式效率
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
