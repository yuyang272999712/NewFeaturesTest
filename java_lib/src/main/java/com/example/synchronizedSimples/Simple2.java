package com.example.synchronizedSimples;

import java.util.ArrayList;

/**
 * synchronized代码块
 *     synObject可以是this，代表获取当前对象的锁，也可以是类中的一个属性，代表获取该属性的锁。（注：两个锁不是同一个锁，可同时运行，
 * 例如下面代码，线程1与线程2访问的同步方法的锁一个是类中的属性、一个是this，锁的不是同一个对象，所以能同时运行）
 *
 *     另外，每个类也会有一个锁，它可以用来控制对static数据成员的并发访问。
 * 并且如果一个线程执行一个对象的非static synchronized方法，另外一个线程需要执行这个对象所属类的static synchronized方法，
 * 此时不会发生互斥现象，因为访问static synchronized方法占用的是类锁，而访问非static synchronized方法占用的是对象锁，
 * 所以不存在互斥现象。
 */
public class Simple2 {
    public static void main(String[] args){
        final DataOpt2 dateOpt = new DataOpt2();

        Thread thread1 = new Thread("线程1"){
            @Override
            public void run() {
                try {
                    dateOpt.insert(this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread2 = new Thread("线程2"){
            @Override
            public void run() {
                try {
                    dateOpt.add(this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread3 = new Thread("线程3"){
            @Override
            public void run() {
                try {
                    dateOpt.delete(this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread4 = new Thread("线程4"){
            @Override
            public void run() {
                try {
                    dateOpt._insert(this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        //thread1与thread2同时调用同步方法，但两个人的锁不是同一个，所以可以同时执行
        thread1.start();
        thread2.start();
        thread3.start();
        //thread1与thread4同时调用同步方法,由于两个同步代码块争取的是同一个锁，所以两个线程必定有一个需要等待
        thread4.start();
    }
}

class DataOpt2 {
    private long currentTime = System.currentTimeMillis();
    private ArrayList<Integer> arrayList = new ArrayList<Integer>();

    public void insert(Thread thread) throws InterruptedException {
        System.out.println(thread.getName()+"在insert数据＋＋＋＋＋＋＋＋＋");
        synchronized(arrayList) {
            arrayList.add(1);
            Thread.sleep(3000);
        }
        System.out.println(thread.getName()+"insert耗时：3秒"+",线程经历了："+(System.currentTimeMillis()-currentTime)+"毫秒");
    }

    public void _insert(Thread thread) throws InterruptedException {
        System.out.println(thread.getName()+"在_insert数据＋＋＋＋＋＋＋＋＋");
        synchronized(arrayList) {
            arrayList.add(1);
            Thread.sleep(3000);
        }
        System.out.println(thread.getName()+"insert耗时：3秒"+",线程经历了："+(System.currentTimeMillis()-currentTime)+"毫秒");
    }

    public synchronized void add(Thread thread) throws InterruptedException {
        System.out.println(thread.getName()+"在add数据＋＋＋＋＋＋＋＋＋");
        synchronized(this) {
            arrayList.add(2);
            Thread.sleep(3000);
        }
        System.out.println(thread.getName()+"add耗时：3秒"+",线程经历了："+(System.currentTimeMillis()-currentTime)+"毫秒");
    }

    public void delete(Thread thread) throws InterruptedException {
        System.out.println(thread.getName()+"在delete数据－－－－－－－－");
        if (arrayList.size()>0){
            arrayList.remove(0);
        }
        Thread.sleep(1000);
        System.out.println(thread.getName()+"delete耗时：1秒"+",线程经历了："+(System.currentTimeMillis()-currentTime)+"毫秒");
    }
}