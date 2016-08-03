package com.example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier
 * 继续上面的例子改造：
 * 我们改造下我们的门禁，毕竟刷卡好不现实，现在需求是这样的：学生一个人走太危险，现在门卫放学在门口守着，让学生3个一组的走。
 */
public class CyclicBarrierSimple2 {
    //学生总数
    private static final int STUDENT_COUNT = 12;
    //每3个人一组放行
    private static CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
        @Override
        public void run() {
            System.out.println("有3个学生到齐了，放行....");
        }
    });

    public static void goHome() throws BrokenBarrierException, InterruptedException {
        System.out.println(Thread.currentThread().getName() + "已刷卡，等待放行~");
        barrier.await();
        System.out.println(Thread.currentThread().getName() + "放学回家~");
    }

    public static void main(String[] args){
        for (int i=0; i<STUDENT_COUNT; i++){
            final int finalI = i;
            new Thread("学生"+ finalI){
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000* finalI);
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
