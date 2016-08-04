package com.example;

import java.util.Timer;
import java.util.TimerTask;

/**
    Timer管理延时任务的缺陷
 a、以前在项目中也经常使用定时器，比如每隔一段时间清理项目中的一些垃圾文件，每个一段时间进行数据清洗；然而Timer是存在
一些缺陷的，因为Timer在执行定时任务时只会创建一个线程，所以如果存在多个任务，且任务时间过长，超过了两个任务的间隔时间，
会发生一些缺陷：因为Timer内部是一个线程，而任务1所需的时间超过了两个任务间的间隔导致，那么任务2就会延时执行。
 b、如果TimerTask抛出RuntimeException，Timer会停止所有任务的运行
 */
public class TimerSimple {
    private static long start;//记录开始执行时的时间

    public static void main(String[] args){
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

        Timer timer = new Timer();
        start = System.currentTimeMillis();
        timer.schedule(timerTask1, 1000);//任务1一秒后开始执行，但任务1耗时五秒
        timer.schedule(timerTask2, 3000);//任务2三秒后开始执行，但任务需要等待先运行的任务1执行完成后才能开始
    }
}
