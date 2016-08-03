package com.example;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
    FutureTask模拟，用户在线观看电子书的预加载功能
 用户观看当前页时，后台预先把下一页加载好，这样可以大幅度提高用户的体验，不需要每一页都等待加载，用户会觉得此电子书软件很流畅
 */
public class FutureSimple2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        BookInstance bookInstance = new BookInstance(1);
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            String content = bookInstance.getCurrentPageContent();
            System.out.println("[1秒阅读时间]read:" + content);
            Thread.sleep(1000);
            System.out.println(System.currentTimeMillis() - start);
        }

    }

    private static class BookInstance{
        //当前页码
        private int currentPage = 1;
        /**
         * 异步获取当前页的内容
         */
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("正在加载第"+currentPage+"页数据");
                Thread.sleep(2000);
                return "Page " + currentPage + " : the content ....";
            }
        });
        /**
         * 实例化一本书，并传入当前读到的页码
         * @param currentPage
         */
        public BookInstance(int currentPage) {
            this.currentPage = currentPage;
            /**
             * 直接启动线程获取当前页码内容
             */
            Thread thread = new Thread(futureTask);
            thread.start();
        }

        public String getCurrentPageContent() throws ExecutionException, InterruptedException {
            String content = futureTask.get();
            this.currentPage = currentPage+1;
            Thread thread = new Thread(futureTask = new FutureTask<String>(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println("正在加载第"+currentPage+"页数据");
                    Thread.sleep(2000);
                    return "Page " + currentPage + " : the content ....";
                }
            }));
            thread.start();
            return content;
        }

    }
}
