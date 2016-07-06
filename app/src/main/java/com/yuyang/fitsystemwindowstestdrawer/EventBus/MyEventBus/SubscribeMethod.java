package com.yuyang.fitsystemwindowstestdrawer.EventBus.MyEventBus;

import java.lang.reflect.Method;

/**
 * 封装：方法、运行线程、注册对象
 */
public class SubscribeMethod {
    Method method;
    ThreadMode threadMode;
    Object subscriber;

    public SubscribeMethod(Method method, ThreadMode threadMode, Object subscriber) {
        this.method = method;
        this.threadMode = threadMode;
        this.subscriber = subscriber;
    }
}
