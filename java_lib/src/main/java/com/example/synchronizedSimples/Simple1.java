package com.example.synchronizedSimples;

import java.util.ArrayList;

/**
 *  对象锁：
 *     当一个线程正在访问一个对象的synchronized方法，那么其他线程不能访问该对象的其他synchronized方法。这个原因很简单，
 * 因为一个对象只有一把锁，当一个线程获取了该对象的锁之后，其他线程无法获取该对象的锁，所以无法访问该对象的其他synchronized方法。
 *     当一个线程正在访问一个对象的synchronized方法，那么其他线程能访问该对象的非synchronized方法。这个原因很简单，
 * 访问非synchronized方法不需要获得该对象的锁，假如一个方法没用synchronized关键字修饰，说明它不会使用到临界资源，
 * 那么其他线程是可以访问这个方法的。
 */
public class Simple1 {
    public static void main(String[] args){
        final DataOpt dateOpt = new DataOpt();

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
        //thread1与thread2同时调用同步方法，thread2会等待thread1执行完后才能获取锁
        thread1.start();
        thread2.start();
        thread3.start();
    }
}

class DataOpt {
    private long currentTime = System.currentTimeMillis();
    private ArrayList<Integer> arrayList = new ArrayList<Integer>();

    public synchronized void insert(Thread thread) throws InterruptedException {
        System.out.println(thread.getName()+"在insert数据＋＋＋＋＋＋＋＋＋");
        arrayList.add(1);
        Thread.sleep(3000);
        System.out.println(thread.getName()+"insert耗时：3秒"+",线程经历了："+(System.currentTimeMillis()-currentTime)+"毫秒");
    }

    public synchronized void add(Thread thread) throws InterruptedException {
        System.out.println(thread.getName()+"在add数据＋＋＋＋＋＋＋＋＋");
        arrayList.add(2);
        Thread.sleep(3000);
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
