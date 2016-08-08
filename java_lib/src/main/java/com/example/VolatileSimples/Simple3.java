package com.example.VolatileSimples;

/**
 * volatile无法保证原子性
 */
public class Simple3 {
    /**
     * 该程序不会输出我们认为的最后结果10000
     */

    public volatile int inc = 0;

    public void increase() {
        inc++;
    }

    public static void main(String[] args){
        final Simple3 test = new Simple3();
        for (int i=0; i<10; i++){
            new Thread(){
                @Override
                public void run() {
                    for (int j=0; j<1000; j++){
                        test.increase();
                    }
                    System.out.println(Thread.currentThread().getName()+"执行完成");
                }
            }.start();
        }
        while (Thread.activeCount() > 2){ //保证前面的线程都执行完
            Thread.yield();
        }
        //以下代码纯属看看最后剩了那两个线程，妈的按说只剩下一个啊
        Thread[] list = new Thread[2];
        Thread.currentThread().getThreadGroup().enumerate(list);
        for (Thread thread:list){
            System.out.println("最后的Thread名称："+thread.getName());
        }
        System.out.println("最后的值："+test.inc);
    }
}
