package com.example;

import java.util.concurrent.Semaphore;

/**
 *   Semaphore中管理着一组虚拟的许可，许可的初始数量可通过构造函数来指定【new Semaphore(1);】，
 * 执行操作时可以首先获得许可【semaphore.acquire();】，并在使用后释放许可【semaphore.release();】。
 * 如果没有许可，那么acquire方法将会一直阻塞直到有许可（或者直到被终端或者操作超时）。
 *   作用：可以用来控制同时访问某个特定资源的操作数量，或者某个操作的数量。
 */
public class SemaphoreSimple {
    /**
     * 定义初始值为1的信号量
     */
    private Semaphore semaphore = new Semaphore(1);

    public void print(String str) throws InterruptedException {
        //请求许可
        semaphore.acquire();

        System.out.println(Thread.currentThread().getName()+"开始耗时任务－－－－－");
        System.out.println(Thread.currentThread().getName()+"正在打印："+ str + "－－－－－");
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName()+"结束耗时任务－－－－－");
        System.out.println();

        //释放许可
        semaphore.release();
    }

    public static void main(String[] args){
        final SemaphoreSimple simple = new SemaphoreSimple();

        //开启10个耗时线程
        for (int i=0; i<10; i++){
            new Thread(){
                @Override
                public void run() {
                    try {
                        simple.print("hello");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
