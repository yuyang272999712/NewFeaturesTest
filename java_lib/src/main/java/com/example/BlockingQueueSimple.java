package com.example;

import java.util.concurrent.ArrayBlockingQueue;

/**
 自从Java 1.5之后，在java.util.concurrent包下提供了若干个阻塞队列，主要有以下几个：
 　　ArrayBlockingQueue：基于数组实现的一个阻塞队列，在创建ArrayBlockingQueue对象时必须制定容量大小。
        并且可以指定公平性与非公平性，默认情况下为非公平的，即不保证等待时间最长的队列最优先能够访问队列。
 　　LinkedBlockingQueue：基于链表实现的一个阻塞队列，在创建LinkedBlockingQueue对象时如果不指定容量大小，
        则默认大小为Integer.MAX_VALUE。
 　　PriorityBlockingQueue：以上2种队列都是先进先出队列，而PriorityBlockingQueue却不是，它会按照元素的优先级
        对元素进行排序，按照优先级顺序出队，每次出队的元素都是优先级最高的元素。注意，此阻塞队列为无界阻塞队列，
        即容量没有上限（通过源码就可以知道，它没有容器满的信号标志），前面2种都是有界队列。
 　　DelayQueue：基于PriorityQueue，一种延时阻塞队列，DelayQueue中的元素只有当其指定的延迟时间到了，才能够从
        队列中获取到该元素。DelayQueue也是一个无界队列，因此往队列中插入数据的操作（生产者）永远不会被阻塞，
        而只有获取数据的操作（消费者）才会被阻塞。
 *
 * 插入方法：add(e)（添加失败会抛出异常）、offer(e)（添加失败返回特殊值）、put(e)（添加失败会一直阻塞）
 * 移除方法：remove(e)（移除失败会抛出异常）、poll(e)（移除失败会返回特殊值）、take(e)（移除失败会一直阻塞）

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
