package com.yuyang.fitsystemwindowstestdrawer.EventBus.MyEventBus;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * TODO yuyang 简单实现EventBus框架
 */
public class EventBus {
    private static EventBus mInstance = new EventBus();
    //UI线程上的Handler
    private Handler mHandler;
    /**
     * 存储所有已订阅的方法
     * key － class，方法的参数
     * value － CopyOnWriteArrayList<SubscribeMethod>，方法、方法所在对象、运行模式的封装
     */
    private static Map<Class, CopyOnWriteArrayList<SubscribeMethod>> mSubscribeMethodsByEventType = new HashMap<>();
    /**
     * 线程局部变量(ThreadLocal)其实的功用非常简单，就是为每一个使用该变量的线程都提供一个变量值的副本，
     * 是Java中一种较为特殊的线程绑定机制，是***每一个线程都可以独立地改变自己的副本***，而不会和其它线程的副本冲突。
     */
    private static ThreadLocal<PostingThread> mPostingThread = new ThreadLocal<PostingThread>(){
        //返回此线程局部变量的当前线程副本中的值，如果这是线程第一次调用该方法，则创建并初始化此副本。
        @Override
        public PostingThread get() {
            return new PostingThread();
        }
    };

    private EventBus(){
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static EventBus getInstance(){
        return mInstance;
    }

    /**
     * 注册订阅者
     * @param subscriber
     */
    public void register(Object subscriber){
        Class clazz = subscriber.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        CopyOnWriteArrayList<SubscribeMethod> subscribeMethods = null;
        for (Method method:methods){
            String methodName = method.getName();
            /**
             * 判断方法是否以onEvent的开头
             */
            if (methodName.startsWith("onEvent")){
                SubscribeMethod subscribeMethod = null;
                //从方法命中提取什么线程运行。默认在UI线程
                String threadMode = methodName.substring("onEvent".length());
                ThreadMode mode = ThreadMode.UI;
                //获取方法的所有参数
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1){
                    Class<?> eventType = parameterTypes[0];
                    synchronized (this){
                        //方法参数是Key
                        if (mSubscribeMethodsByEventType.containsKey(eventType)){
                            subscribeMethods = mSubscribeMethodsByEventType.get(eventType);
                        }else {
                            subscribeMethods = new CopyOnWriteArrayList<>();
                            mSubscribeMethodsByEventType.put(eventType, subscribeMethods);
                        }
                    }
                    if (threadMode.equals("Async")){
                        mode = ThreadMode.Async;
                    }
                    //提取出method，mode，方法所在类对象，存数的类型封装为SubscribeMethod
                    subscribeMethod = new SubscribeMethod(method, mode, subscriber);
                    subscribeMethods.add(subscribeMethod);
                }
            }
        }
    }

    /**
     * 注销订阅者
     * @param subscriber
     */
    public void unregister(Object subscriber){
        Class clazz = subscriber.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method:methods){
            String methodName = method.getName();
            if (methodName.startsWith("onEvent")){
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1){
                    mSubscribeMethodsByEventType.remove(parameterTypes[0]);
                }
            }
        }
    }

    /**
     * 发布事件
     * ＊＊＊post方法可能没在UI线程中被调用
     * @param eventTypeInstance
     */
    public void post(Object eventTypeInstance){
        //拿到该线程中的PostingThread对象
        PostingThread postingThread = mPostingThread.get();
        postingThread.isMainThread = Looper.getMainLooper() == Looper.myLooper();
        //将事件加入事件队列
        List<Object> eventQueue = postingThread.mEventQueue;
        eventQueue.add(eventTypeInstance);
        //防止多次调用
        if (postingThread.isPosting){
            return;
        }
        postingThread.isPosting = true;
        //取出所有事件进行调用
        while (!eventQueue.isEmpty()){
            Object eventType = eventQueue.remove(0);
            postEvent(eventType, postingThread);
        }
        postingThread.isPosting = false;
    }

    private void postEvent(final Object eventType, PostingThread postingThread) {
        CopyOnWriteArrayList<SubscribeMethod> subscribeMethods = null;
        synchronized (this){
            subscribeMethods = mSubscribeMethodsByEventType.get(eventType.getClass());
        }
        for (final SubscribeMethod subscribeMethod:subscribeMethods){
            if (subscribeMethod.threadMode == ThreadMode.UI){
                if (postingThread.isMainThread){
                    invokeMethod(eventType, subscribeMethod);
                }else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            invokeMethod(eventType, subscribeMethod);
                        }
                    });
                }
            }else {
                new AsyncTask<Void,Void,Void>(){
                    @Override
                    protected Void doInBackground(Void... params) {
                        invokeMethod(eventType, subscribeMethod);
                        return null;
                    }
                }.execute();
            }
        }
    }

    private void invokeMethod(Object eventType, SubscribeMethod subscribeMethod) {
        try {
            subscribeMethod.method.invoke(subscribeMethod.subscriber, eventType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
