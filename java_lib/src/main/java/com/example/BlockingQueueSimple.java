package com.example;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 阻塞队列应用实例
 */
public class BlockingQueueSimple {
    private int i = 0;
    private int queueSize = 10;
    private ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(queueSize, true);

    public static void main(String[] args){
        BlockingQueueSimple simple = new BlockingQueueSimple();
        Consumer consumer = simple.new Consumer();
        Producer producer = simple.new Producer();

        consumer.start();
        producer.start();
    }

    class Consumer extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(2000);
                    Integer item = queue.take();
                    System.out.println("从队列取走一个元素:"+item+"，队列剩余"+queue.size()+"个元素");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Producer extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    queue.put(i);
                    System.out.println("向队列取中插入一个元素:"+i+"，队列剩余空间："+(queueSize-queue.size()));
                    i++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
