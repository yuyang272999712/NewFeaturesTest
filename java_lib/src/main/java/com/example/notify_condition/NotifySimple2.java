package com.example.notify_condition;

import java.util.PriorityQueue;

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
public class NotifySimple2 {
    private int queueSize = 10;
    private PriorityQueue<Integer> queue = new PriorityQueue<Integer>(queueSize);

    public static void main(String[] args){
        NotifySimple2 simple2 = new NotifySimple2();

        Producer producer = simple2.new Producer();
        Consumer consumer = simple2.new Consumer();

        producer.start();
        consumer.start();
    }

    private class Producer extends Thread {
        @Override
        public void run() {
            while (true){
                synchronized (queue){
                    try {
                        if (queue.size() == queueSize){
                            System.out.println("队列满，等待有空余空间");
                            queue.wait();
                        }
                        queue.offer(1);
                        queue.notify();
                        System.out.println("向队列取中插入一个元素，队列剩余空间："+(queueSize-queue.size()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private class Consumer extends Thread {
        public void run() {
            while (true){
                synchronized (queue){
                    try {
                        if (queue.size() == 0){
                            System.out.println("队列空，等待数据");
                            queue.wait();
                        }
                        queue.poll();
                        queue.notify();
                        System.out.println("从队列取走一个元素，队列剩余"+queue.size()+"个元素");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
