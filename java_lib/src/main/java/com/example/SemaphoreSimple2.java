package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Semaphore模拟线程管理
        假设现在数据库连接池最大连接数为10，当10个连接都分配出去以后，现在有用户继续请求连接，可能的处理：
    a、手动抛出异常，用户界面显示，服务器忙，稍后再试
    b、阻塞，等待其他连接的释放
        从用户体验上来说，更好的选择当然是阻塞，等待其他连接的释放，用户只会觉得稍微慢了一点，并不影响他的操作。
    下面使用Semaphore模拟实现一个数据库连接池：
 */
public class SemaphoreSimple2 {
    //模拟线程池中最多有三个等待线程
    private final List<Connect> pool = new ArrayList<Connect>(3);
    //信号量与线程池中线程数量一致
    private final Semaphore semaphore = new Semaphore(3);

    /**
     * 构造函数中初始化线程池
     */
    public SemaphoreSimple2(){
        pool.add(new Connect("1"));
        pool.add(new Connect("2"));
        pool.add(new Connect("3"));
    }

    /**
     * 请求分配链接
     * @return
     * @throws InterruptedException
     */
    public Connect getConn() throws InterruptedException {
        //请求信号量
        semaphore.acquire();

        Connect connect = null;
        synchronized (pool){
            connect = pool.remove(0);
        }
        System.out.println(Thread.currentThread().getName()+"线程获取了一个链接："+connect+"－－－－－－");
        return connect;
    }

    public void release(Connect connect){
        synchronized (pool){
            pool.add(connect);
        }
        System.out.println(Thread.currentThread().getName()+"线程释放了一个链接："+connect);
        semaphore.release();
    }

    public static void main(String[] args){
        final SemaphoreSimple2 simple2 = new SemaphoreSimple2();

        /**
         * 此线程长期占有一个链接
         */
        new Thread(){
            @Override
            public void run() {
                try {
                    Connect connect = simple2.getConn();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        /**
         * 再启动8个线程，每个线程耗时3秒，执行完后释放信号量
         */
        for (int i=0; i<8; i++){
            new Thread(){
                @Override
                public void run() {
                    try {
                        Connect connect = simple2.getConn();
                        Thread.sleep(3000);
                        simple2.release(connect);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    private class Connect{
        private String connectId;

        public Connect(String connectId){
            this.connectId = connectId;
        }

        @Override
        public String toString() {
            return "Connect{" +
                    "connectId='" + connectId + '\'' +
                    '}';
        }
    }
}
