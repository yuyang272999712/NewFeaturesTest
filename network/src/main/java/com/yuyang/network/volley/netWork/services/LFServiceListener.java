package com.yuyang.network.volley.netWork.services;

/**
 * Created by Glen on 2016/1/5.
 * LFServiceListener
 */
public interface LFServiceListener<T> {
    void onServiceCallBack(ServiceEvent<T> event);
}
