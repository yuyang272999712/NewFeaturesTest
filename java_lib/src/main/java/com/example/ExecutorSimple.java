package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
    Executors的API介绍
 Java类库提供了许多静态方法来创建一个线程池：
 a、newFixedThreadPool 创建一个固定长度的线程池，当到达线程最大数量时，线程池的规模将不再变化。
 b、newCachedThreadPool 创建一个可缓存的线程池，如果当前线程池的规模超出了处理需求，将回收空的线程；当需求增加时，会增加线程数量；线程池规模无限制。
 c、newSingleThreadPoolExecutor 创建一个单线程的Executor，确保任务对了，串行执行
 d、newScheduledThreadPool 创建一个固定长度的线程池，而且以延迟或者定时的方式来执行，类似Timer；后面后单独使用Blog介绍它与Timer区别
 */
public class ExecutorSimple {
    private static final int THREAD_COUNT = 10;
    private static final ExecutorService exec = Executors.newFixedThreadPool(THREAD_COUNT);

    //ZHU yuyang 没事别运行这个，这是在服务器上跑的！！！
    public static void main(String[] args) throws IOException {
        //创建服务端的ServerSocket等待客户端的请求
        ServerSocket server = new ServerSocket(8080);
        while (true){
            //接受客户端请求，如果没有就进入阻塞状态，等待客户端请求
            final Socket client = server.accept();
            Runnable task = new Runnable(){
                @Override
                public void run() {
                    handleReq(client);
                }
            };
            exec.execute(task);
        }
    }

    protected static void handleReq(Socket client) {
        long beginTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName()+"开始执行耗时任务....");
        while (true){
            if (System.currentTimeMillis() - beginTime > 5000){
                break;
            }
        }
        System.out.println(Thread.currentThread().getName()+"执行结束！！！！");
    }
}
