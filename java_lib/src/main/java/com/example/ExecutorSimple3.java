package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
    ExecutorService同时执行多个线程
 */
public class ExecutorSimple3 {
    private static final int THREAD_COUNT = 10;
    private static final ExecutorService exec = Executors.newFixedThreadPool(THREAD_COUNT);

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        List<Callable<String>> tasks = new ArrayList<>();
        Callable<String> task = null;
        for (int i=0; i<THREAD_COUNT; i++){
            task = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    int ran = new Random().nextInt(10000);
                    Thread.sleep(ran);
                    System.out.println(Thread.currentThread().getName()+" 休息了 " + ran );
                    return ran+"";
                }
            };
            tasks.add(task);
        }

        long s = System.currentTimeMillis();
        //TODO yuyang invokeAll返回是等待所有线程执行完毕的，这样会阻塞主线程
        List<Future<String>> futures = exec.invokeAll(tasks);
        System.out.println("执行任务消耗了 ：" + (System.currentTimeMillis() - s) +"毫秒");
        for (int i=0; i<futures.size(); i++){
            System.out.println(futures.get(i).get());
        }

        exec.shutdown();
    }

}
