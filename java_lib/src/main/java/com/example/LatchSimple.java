package com.example;

import java.util.concurrent.CountDownLatch;

/**
 CountDownLatch：

    Latch闭锁的意思，是一种同步的工具类。类似于一扇门：在闭锁到达结束状态之前，这扇门一直是关闭着的，不允许任何线程通过，
 当到达结束状态时，这扇门会打开并允许所有的线程通过。且当门打开了，就永远保持打开状态。
    作用：可以用来确保某些活动直到其他活动都完成后才继续执行。
 使用场景：
     1、某个操作需要的资源初始化完毕
     2、某个服务依赖的线程全部开启等等...

    CountDownLatch是一种灵活的闭锁实现，包含一个计数器，该计算器初始化为一个正数，表示需要等待事件的数量。
 countDown方法递减计数器，表示有一个事件发生，而await方法等待计数器到达0，表示所有需要等待的事情都已经完成。
 */
public class LatchSimple {

    private static CountDownLatch latch = new CountDownLatch(3);

    public static void main(String[] args) throws InterruptedException {
        new Thread(){
            @Override
            public void run() {
                System.out.println("耗时操作开始－－－－1");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("耗时操作:耗时2000毫秒－－－－1");
                latch.countDown();
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                System.out.println("耗时操作开始－－－－2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("耗时操作:耗时100毫秒－－－－2");
                latch.countDown();
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                System.out.println("耗时操作开始－－－－3");
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("耗时操作:耗时800毫秒－－－－3");
                latch.countDown();
            }
        }.start();

        latch.await();

        System.out.println("所有线程都执行完成了！！！");
    }
}
