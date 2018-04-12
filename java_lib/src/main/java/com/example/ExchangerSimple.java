package com.example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Exchanger;

/**
 * Exchanger是一个线程间交换数据的工具类。
 *  Exchanger提供一个同步点，在这个同步点上，两个线程可以交换彼此的数据。这两个线程可以通过exchange方法交换数据，
 * 当然存在线程执行不同步的情况，如果第一个线程先到达同步点，那么在第二个线程到达同步点之前，第一个线程会阻塞等待，
 * 直到两个线程都到达同步点，两个线程就可以使用exchange方法交换彼此的数据。
 */

public class ExchangerSimple {
    /**
     * 交换器
     */
    public static Exchanger<String> exchanger = new Exchanger<>();

    public static DateFormat format = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args){
        new Thread("线程1"){
            @Override
            public void run() {
                try {
                    String strA = "信息来自于线程1";
                    System.out.println(Thread.currentThread().getName()+" 到达同步点："+format.format(new Date()));
                    //调用exchange方法表示当前线程已经到达了同步点
                    String msgFromB = exchanger.exchange(strA);
                    System.out.println(Thread.currentThread().getName()+" 结束："+format.format(new Date()));
                    System.out.println(Thread.currentThread().getName()+" 从线程2得到的信息："+msgFromB);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread("线程2"){
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    String strB = "信息来自于线程2";
                    System.out.println(Thread.currentThread().getName()+" 到达同步点："+format.format(new Date()));
                    //调用exchange方法表示当前线程已经到达了同步点
                    String msgFromA = exchanger.exchange(strB);
                    System.out.println(Thread.currentThread().getName()+" 结束："+format.format(new Date()));
                    System.out.println(Thread.currentThread().getName()+" 从线程1得到的信息："+msgFromA);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
