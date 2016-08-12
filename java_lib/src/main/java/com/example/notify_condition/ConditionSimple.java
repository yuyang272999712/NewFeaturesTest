package com.example.notify_condition;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
    Condition是在java 1.5中才出现的，它用来替代传统的Object的wait()、notify()实现线程间的协作，相比使用Object
 的wait()、notify()，使用Condition1的await()、signal()这种方式实现线程间协作更加安全和高效。因此通常来说比较
 推荐使用Condition，在阻塞队列那一篇博文中就讲述到了，阻塞队列实际上是使用了Condition来模拟线程间协作。
    Condition是个接口，基本的方法就是await()和signal()方法；
    Condition依赖于Lock接口，生成一个Condition的基本代码是lock.newCondition()
    调用Condition的await()和signal()方法，都必须在lock保护之内，就是说必须在lock.lock()和lock.unlock之间才
 可以使用
     　　Condition中的await()对应Object的wait()；
     　　Condition中的signal()对应Object的notify()；
     　　Condition中的signalAll()对应Object的notifyAll()。
 */
//使用Condition实现生产消费者模式
public class ConditionSimple {
    private int queueSize = 10;
    private PriorityQueue<Integer> queue = new PriorityQueue<Integer>(queueSize);
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public static void main(String[] args){
        ConditionSimple simple = new ConditionSimple();
        Producer producer = simple.new Producer();
        Consumer consumer = simple.new Consumer();

        producer.start();
        consumer.start();
    }

    //消费者
    private class Consumer extends Thread {
        @Override
        public void run() {
            while (true){
                lock.lock();
                try {
                    Thread.sleep(2000);
                    while (queue.size() == 0) {
                        try {
                            System.out.println("队列空，等待数据");
                            notEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.poll();//取队列第一个
                    notFull.signal();
                    System.out.println("从队列取走一个元素，队列剩余" + queue.size() + "个元素");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    //生产者
    private class Producer extends Thread {
        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (queue.size() == queueSize){
                        try {
                            System.out.println("队列满，等待有空余空间");
                            notFull.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.offer(1);//插入一个元素到队列
                    notEmpty.signal();
                    System.out.println("向队列取中插入一个元素，队列剩余空间："+(queueSize-queue.size()));
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
