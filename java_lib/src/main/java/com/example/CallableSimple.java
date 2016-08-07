package com.example;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
    Callable位于java.util.concurrent包下，它也是一个接口，在它里面也只声明了一个方法，只不过这个方法叫做call()：
        public interface Callable<V> {
            V call() throws Exception;
        }
    可以看到，这是一个泛型接口，call()函数返回的类型就是传递进来的V类型。
    那么怎么使用Callable呢？一般情况下是配合ExecutorService来使用的，在ExecutorService接口中声明了若干个
 submit方法的重载版本：
        <T> Future<T> submit(Callable<T> task);
        <T> Future<T> submit(Runnable task, T result);
        Future<T> submit(Runnable task);
    第一个submit方法里面的参数类型就是Callable。
    暂时只需要知道Callable一般是和ExecutorService配合来使用的，具体的使用方法讲在后面讲述。
    一般情况下我们使用第一个submit方法和第三个submit方法，第二个submit方法很少使用。
 */
//使用Callable+Future获取执行结果
public class CallableSimple {
    private static ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args){
        Task task = new Task();
        Future<Integer> future = executor.submit(task);

        System.out.println("主线程在执行任务:"+Thread.currentThread().getName()+"－－－－");

        try {
            System.out.println("task运行结果:"+future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        System.out.println("主线程 和 子线程 任务执行完毕！");
    }
}

class Task implements Callable<Integer>{
    @Override
    public Integer call() throws Exception {
        System.out.println("子线程在进行计算:"+Thread.currentThread().getName());
        Thread.sleep(3000);
        int sum = 0;
        for(int i=0;i<100;i++)
            sum += i;
        return sum;
    }
}
