package com.example.VolatileSimples;

/**
 * volatile无法保证原子性
 *
 * ZHU yuyang
 *  一下类型可以保证原子操作，原子操作类：
 *      原子方式更新基本类型，共包括3个类：
         AtomicBoolean：原子更新布尔变量
         AtomicInteger：原子更新整型变量
         AtomicLong：原子更新长整型变量
        原子更新数组里的某个元素，共有3个类：
         AtomicIntegerArray：原子更新整型数组的某个元素
         AtomicLongArray：原子更新长整型数组的某个元素
         AtomicReferenceArray：原子更新引用类型数组的某个元素
        原子更新引用类型
         AtomicReference：原子更新引用类型
         AtomicReferenceFieldUpdater：原子更新引用类型里的字段
         AtomicMarkableReference：原子更新带有标记位的引用类型。
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
            new Thread("自增线程"+i){
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
