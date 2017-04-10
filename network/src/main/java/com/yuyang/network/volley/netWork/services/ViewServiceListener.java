package com.yuyang.network.volley.netWork.services;

/**
 * Created by youj on 2015/05/10.
 */
public interface ViewServiceListener {
    void processView(boolean isFail, LFServiceError serviceError, boolean isProcessServiceError);
}
