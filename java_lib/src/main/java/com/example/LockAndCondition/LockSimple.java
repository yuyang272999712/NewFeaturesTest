package com.example.LockAndCondition;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  Lock锁机制是JDK 5之后新增的锁机制，不同于内置锁，Lock锁必须显式声明，并在合适的位置释放锁。
 *  Lock是一个接口，其由三个具体的实现：ReentrantLock、ReetrantReadWriteLock.ReadLock 和
 * ReetrantReadWriteLock.WriteLock，即重入锁、读锁和写锁。
 *  内置锁必须在释放锁的代码块中释放，虽然简化了锁的使用，但是却造成了其他等待获取锁的线程必须依靠阻塞等待的
 * 方式获取锁，也就是说内置锁实际上是一种阻塞锁。而新增的Lock锁机制则是一种非阻塞锁。
 *
 *  Lock接口方法：
 *       //无条件获取锁
         void lock();
         //获取可响应中断的锁
         //在获取锁的时候可响应中断，中断的时候会抛出中断异常
         void lockInterruptibly() throws InterruptedException;
         //轮询锁。如果不能获得锁，则采用轮询的方式不断尝试获得锁
         boolean tryLock();
         //定时锁。如果不能获得锁，则每隔unit的时间就会尝试重新获取锁
         boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
         //释放获得锁
         void unlock();
         //获取绑定的Lock实例的条件变量。在等待某个条件变量满足的之
         //前，lock实例必须被当前线程持有。调用Condition的await方法
         //会自动释放当前线程持有的锁
         Condition newCondition();
    使用Lock锁的示例代码如下：
         Lock lock = new ReentrantLock();
         lock.lock();
         try {
             //更新对象状态
             //如果有异常则捕获异常
             //必要时恢复不变性条件
             //如果由return语句必须放在这里
         }finally {
            lock.unlock();
         }

    简单总结ReentrantLock和synchronized，前者的先进性体现在以下几点：
        1、可响应中断的锁。当在等待锁的线程如果长期得不到锁，那么可以选择不继续等待而去处理其他事情，
      而synchronized的互斥锁则必须阻塞等待，不能被中断
        2、可实现公平锁。所谓公平锁指的是多个线程在等待锁的时候必须按照线程申请锁的时间排队等待，
      而非公平性锁则保证这点，每个线程都有获得锁的机会。synchronized的锁和ReentrantLock使用的默认锁都是
      非公平性锁，但是ReentrantLock支持公平性的锁，在构造函数中传入一个boolean变量指定为true实现的就是
      公平性锁。不过一般而言，使用非公平性锁的性能优于使用公平性锁
        3、每个synchronized只能支持绑定一个条件变量，这里的条件变量是指线程执行等待或者通知的条件，
      而ReentrantLock支持绑定多个条件变量，通过调用lock.newCondition()可获取多个条件变量。不过使用多少个
      条件变量需要依据具体情况确定。

    如何在ReentrantLock和synchronized之间进行选择：
        在一些内置锁无法满足一些高级功能的时候才考虑使用ReentrantLock。这些高级功能包括：可定时的、可轮询的与
      可中断的锁获取操作，公平队列，以及非块结构的锁。否则还是应该优先使用synchronized。
        原因：synchronized未来还将继续优化，目前的synchronized已经进行了自适应、自旋、锁消除、锁粗化、轻量级
      锁和偏向锁等方面的优化，在线程阻塞和线程唤醒方面的性能已经没有那么大了。另一方面，ReentrantLock的性能可能
      就止步于此，未来优化的可能性很小。


 */

public class LockSimple {
    //锁对象
    private static final Lock lock = new ReentrantLock();
    //日期格式
    private static final DateFormat format = new SimpleDateFormat("HH:mm:ss");

    //写数据
    public void write(){
        lock.lock();
        System.out.println(Thread.currentThread().getName()+":获取锁");
        try {
            System.out.println(Thread.currentThread().getName() + ":开始写数据 " + format.format(new Date()));
            long start = System.currentTimeMillis();
            for (; ; ) {
                //写15秒钟
                if (System.currentTimeMillis() - start > 1500) {
                    break;
                }
            }
            System.out.println(Thread.currentThread().getName() + ":写完成 " + format.format(new Date()));
        }finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName()+":释放锁");
        }
    }

    //读数据
    public void read() throws InterruptedException {
        lock.lockInterruptibly();
        System.out.println(Thread.currentThread().getName()+":获取中断锁");
        try {
            System.out.println(Thread.currentThread().getName()+":开始读数据 "+format.format(new Date()));
        }finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName()+":释放锁");
        }
    }

    /**
     * 执行写数据的线程
     */
    static class Writer implements Runnable{
        private LockSimple lockInterruptDemo;

        public Writer(LockSimple lockInterruptDemo) {
            this.lockInterruptDemo = lockInterruptDemo;
        }

        @Override
        public void run() {
            lockInterruptDemo.write();
        }
    }

    /**
     * 执行读数据的线程
     */
    static class Reader implements Runnable{
        private LockSimple lockInterruptDemo;

        public Reader(LockSimple lockInterruptDemo){
            this.lockInterruptDemo = lockInterruptDemo;
        }

        @Override
        public void run() {
            try {
                lockInterruptDemo.read();
                System.out.println(Thread.currentThread().getName()+":读数据完成 "+format.format(new Date()));
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName()+":中断读数据 "+format.format(new Date()));
            }
            System.out.println(Thread.currentThread().getName()+":读线程结束 "+format.format(new Date()));
        }
    }

    public static void main(String[] args){
        LockSimple lockSimple = new LockSimple();
        Thread writer = new Thread(new Writer(lockSimple), "Writer");
        Thread reader = new Thread(new Reader(lockSimple), "Reader");

        writer.start();
        reader.start();

        //运行5秒，然后尝试中断
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(reader.getName() + ":读数据线程不想再等了 " + format.format(new Date()));
        //中断读的线程 ZHU yuyang 这里如果使用synchronized，要等到Reader获取了锁才会执行中断
        reader.interrupt();
    }
}
