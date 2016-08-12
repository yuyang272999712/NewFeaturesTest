package com.example.notify_condition;

/**
 *     如果调用某个对象的wait()方法，当前线程必须拥有这个对象的monitor（即锁），因此调用wait()方法必须在同步块或者
 * 同步方法中进行（synchronized块或者synchronized方法）。
 *     调用某个对象的wait()方法，相当于让当前线程交出此对象的monitor，然后进入等待状态，等待后续再次获得此对象的锁
 *（Thread类中的sleep方法使当前线程暂停执行一段时间，从而让其他线程有机会继续执行，但它并不释放对象锁）；
 *     notify()方法能够唤醒一个正在等待该对象的monitor的线程，当有多个线程都在等待该对象的monitor的话，则只能唤醒
 * 其中一个线程，具体唤醒哪个线程则不得而知。
 *     同样地，调用某个对象的notify()方法，当前线程也必须拥有这个对象的monitor，因此调用notify()方法必须在同步块
 * 或者同步方法中进行（synchronized块或者synchronized方法）。
 *  notifyAll()方法能够唤醒所有正在等待该对象的monitor的线程，这一点与notify()方法是不同的。
 */
public class NotifySimple1 {
    public static Object object = new Object();

    public static void main(String[] args){
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();

        thread1.start();
        thread2.start();

        NotifySimple1 simple1 = new NotifySimple1();
        Thread thread3 = new Thread(){
            @Override
            public void run() {
                simple1.doSomething();
            }
        };
        Thread thread4 = new Thread(){
            @Override
            public void run() {
                simple1.doNoThing();
            }
        };
        thread3.start();
        thread4.start();
    }

    private synchronized void doSomething(){
        System.out.println(Thread.currentThread().getName()+" 争取到了锁");
        try {
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName()+" 调用了lock的wait()方法");
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+" 重新获取了锁");
    }

    private synchronized void doNoThing(){
        System.out.println(Thread.currentThread().getName()+" 争取到了锁");
        notify();
        System.out.println(Thread.currentThread().getName()+" 调用了lock的notify()方法");
    }

    private static class Thread1 extends Thread {
        @Override
        public void run() {
            synchronized (object){
                System.out.println(Thread.currentThread().getName()+" 争取到了锁");
                try {
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName()+" 调用了lock对象的wait()方法");
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" 重新获得了锁");
            }
        }
    }

    private static class Thread2 extends Thread {
        @Override
        public void run() {
            synchronized (object){
                System.out.println(Thread.currentThread().getName()+" 争取到了锁");
                object.notify();
                System.out.println(Thread.currentThread().getName()+" 调用了lock对象的notify()方法");
                System.out.println(Thread.currentThread().getName()+" 释放了锁");
            }
        }
    }
}
