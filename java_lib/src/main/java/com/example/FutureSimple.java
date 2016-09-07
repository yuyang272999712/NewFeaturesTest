package com.example;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
    TODO yuyang Future就是对于具体的Runnable或者Callable任务的执行结果进行取消、查询是否完成、获取结果。必要时可以通过get方法
 获取执行结果，该方法会阻塞直到任务返回结果。

 　　Future类位于java.util.concurrent包下，它是一个接口：
     public interface Future<V> {
         boolean cancel(boolean mayInterruptIfRunning);
         boolean isCancelled();
         boolean isDone();
         V get() throws InterruptedException, ExecutionException;
         V get(long timeout, TimeUnit unit)
         throws InterruptedException, ExecutionException, TimeoutException;
     }

 　　在Future接口中声明了5个方法，下面依次解释每个方法的作用：
  1、cancel方法用来取消任务，如果取消任务成功则返回true，如果取消任务失败则返回false。参数mayInterruptIfRunning
 表示是否允许取消正在执行却没有执行完毕的任务，如果设置true，则表示可以取消正在执行过程中的任务。如果任务已经完成，
 则无论mayInterruptIfRunning为true还是false，此方法肯定返回false，即如果取消已经完成的任务会返回false；
 如果任务正在执行，若mayInterruptIfRunning设置为true，则返回true，若mayInterruptIfRunning设置为false，
 则返回false；如果任务还没有执行，则无论mayInterruptIfRunning为true还是false，肯定返回true。
  2、isCancelled方法表示任务是否被取消成功，如果在任务正常完成前被取消成功，则返回 true。
  3、isDone方法表示任务是否已经完成，若任务完成，则返回true；
  4、get()方法用来获取执行结果，这个方法会产生阻塞，会一直等到任务执行完毕才返回；
  5、get(long timeout, TimeUnit unit)用来获取执行结果，如果在指定时间内，还没获取到结果，就直接返回null。

 　　也就是说Future提供了三种功能：
 　　1）判断任务是否完成；
 　　2）能够中断任务；
 　　3）能够获取任务执行结果。
 ***************************************************************
    FutureTask是Future接口的一个唯一实现类:
        public class FutureTask<V> implements RunnableFuture<V>

 　　FutureTask类实现了RunnableFuture接口，我们看一下RunnableFuture接口的实现：
        public interface RunnableFuture<V> extends Runnable, Future<V> {
            void run();
        }

 　　可以看出RunnableFuture继承了Runnable接口和Future接口，而FutureTask实现了RunnableFuture接口。
 所以它既可以作为Runnable被线程执行，又可以作为Future得到Callable的返回值。

 　　FutureTask提供了2个构造器：
        public FutureTask(Callable<V> callable) {}
        public FutureTask(Runnable runnable, V result) {}
 */
public class FutureSimple {
    /**
     * 使用FutureTask来提前加载稍后要用到的数据
     */
    private final FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
        @Override
        public String call() throws Exception {
            //模拟加载资源需要耗时5秒
            Thread.sleep(5000);
            return "加载资源需要5秒";
        }
    });

    //TODO yuyang 使用Callable+FutureTask的方式获取执行结果，也可以ExecutorService的submit(task);方式启动
    private final Thread thread = new Thread(futureTask);

    /**
     * 开始加载数据
     */
    public void start(){
        thread.start();
    }

    /**
     * 获取要用到的数据
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String getRes() throws ExecutionException, InterruptedException {
        return futureTask.get();//如果加载完毕就直接返回，否则等待加载完毕
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureSimple simple = new FutureSimple();

        //开始加载资源
        simple.start();

        /**
         * 获取资源
         */
        System.out.println(System.currentTimeMillis() + "：我要用预加载的数据！");
        String res = simple.getRes();//如果加载完毕就直接返回，否则等待加载完毕
        System.out.println(res);
        System.out.println(System.currentTimeMillis() + "：加载资源结束");
    }
}
