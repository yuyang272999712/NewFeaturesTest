package com.example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier注解：
 *      A synchronization aid that allows a set of threads to all wait for
 *  each other to reach a common barrier point.  CyclicBarriers are
 *  useful in programs involving a fixed sized party of threads that
 *  must occasionally wait for each other. The barrier is called
 *  <em>cyclic</em> because it can be re-used after the waiting threads
 *  are released.
 *
 *      大概意思：一个让一组线程同时阻塞到一个位置的同步辅助类。在包含固定线程且线程间必须相互等待的场景中非常有用。
 *  cyclic的意思是CyclicBarrier当等待的线程全部释放之后，可以重复使用。
 *  CyclicBarrier 类似一个闸门，指定数目的线程都必须到达这个闸门，闸门才会打开。
 */
public class CyclicBarrierSimple {
    /**
     * 使用CyclicBarrier模拟一个门禁系统：
     * 需求是这样的：到放学时间，所有的学生必须刷卡，然后人数齐了自动开门，统一回家。这个需求刚刚的，避免了把部分孩子
     * 丢在学校发生危险，特别是幼儿园或者小学生~~
     *
     * 记得是阻塞线程，不是阻塞操作，在同一个线程使劲掉await是没什么效果的。
     */
    //学生人数
    private static final int STUDENT_COUNT = 10;
    //人到齐了才放行
    private static CyclicBarrier barrier = new CyclicBarrier(STUDENT_COUNT, new Runnable() {
        @Override
        public void run() {
            System.out.println("人到齐了，开门....");
        }
    });

    public static void goHome() throws BrokenBarrierException, InterruptedException {
        System.out.println(Thread.currentThread().getName() + "已刷卡，等待开门回家~");
        barrier.await();
        System.out.println(Thread.currentThread().getName() + "放学回家~");
    }

    public static void main(String[] args){
        //每个线程模拟一个学生
        for (int i=0; i<STUDENT_COUNT; i++){
            final int finalI = i;
            new Thread("学生" + finalI +"  " ){
                @Override
                public void run() {
                    try {
                        if (finalI == 5) {
                            System.out.println(Thread.currentThread().getName() + "去上厕所了！需要耗时5秒");
                            Thread.sleep(5000);
                        }
                        goHome();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
