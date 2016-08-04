package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
    ScheduledExecutorService代替Timer的使用，可以与TimerSimple.java对比来看，
    ScheduledExecutorService中维护了一个线程池，只要有闲置的线程任务就能按时执行。并且线程之间不会互相干扰，
 即使一个线程出错了，也不会影响其它线程的运行
 */
public class ExecutorSimple2 {
    private static final int THREAD_COUNT = 10;
    private static final ScheduledExecutorService exec = Executors.newScheduledThreadPool(THREAD_COUNT);
    private static long start;//记录开始执行时的时间

    public static void main(String[] args) throws IOException {
        TimerTask timerTask1 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("任务1开始执行! " + (System.currentTimeMillis() - start));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //throw new RuntimeException();
            }
        };
        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("任务2开始执行! " + (System.currentTimeMillis() - start));
            }
        };

        start = System.currentTimeMillis();
        exec.schedule(timerTask1, 1000, TimeUnit.MILLISECONDS);
        exec.schedule(timerTask2, 3000, TimeUnit.MILLISECONDS);
        exec.shutdown();
    }

}
