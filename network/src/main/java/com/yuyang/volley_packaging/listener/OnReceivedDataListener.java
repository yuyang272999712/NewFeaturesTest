package com.yuyang.volley_packaging.listener;

/**
 * 数据返回回调
 */

public interface OnReceivedDataListener<T> {
    void onReceivedData(T response);
}
