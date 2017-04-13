package com.yuyang.network.volley.netWork.services;

import android.text.TextUtils;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.yuyang.MyApplication;
import com.yuyang.network.volley.netWork.base.LFBaseRequest;
import com.yuyang.volley_packaging.NetworkManager;
import com.yuyang.volley_packaging.RequestManager;
import com.yuyang.volley_packaging.annotation.RequestAnnotation;

/**
 * 中间层，解耦Framework发服务方法
 */
public class LFServiceController {

    public static <T> void sendService(LFBaseRequest request, Class<T> responseClass, ServiceCacheControl cacheControl, final LFServiceListener<T> listener) {
        RequestAnnotation requestAnnotation = request.getClass().getAnnotation(RequestAnnotation.class);
        if (requestAnnotation == null) return;

        final String path = requestAnnotation.path();
        if (TextUtils.isEmpty(path))
            return;
        String url = request.getProtocol() + "://" + request.getHost();
        /*if (!TextUtils.isEmpty(request.getPort()+"")){//!--yuyang 实际开发中应该是有端口号的
            url = url + ":" + request.getPort();
        }*/
        if (!TextUtils.isEmpty(request.getPath())) {
            url = url + request.getPath() + path;
        } else {
            url = url + path;
        }
        if (cacheControl == null) {
            //未开启缓存的情况下
            NetworkManager.getInstance(MyApplication.getInstance())
                    .loadData(url, request.getToken(), false, null, 0, 0,
                            request, responseClass, new Response.Listener<T>() {
                                @Override
                                public void onResponse(T response) {
                                    listener.onServiceCallBack(new ServiceEvent(response));
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    listener.onServiceCallBack(new ServiceEvent(getServiceError(error)));
                                }
                            }, null);
        } else {
            //开启缓存
            NetworkManager.getInstance(MyApplication.getInstance())
                    .loadData(url, request.getToken(),
                            true, cacheControl.getCacheKey(), cacheControl.getCacheDuration(), cacheControl.getRefreshDuration(),
                            request, responseClass, new Response.Listener<T>() {
                                @Override
                                public void onResponse(T response) {
                                    listener.onServiceCallBack(new ServiceEvent(response));
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    listener.onServiceCallBack(new ServiceEvent(getServiceError(error)));
                                }
                            }, null);
        }
    }

    public static void clearCache(String key) {
        RequestManager.getInstance(MyApplication.getInstance()).clearRequestCache(key);
    }

    private static LFServiceError getServiceError(VolleyError volleyError) {
        LFServiceError serviceError = new LFServiceError();

        EServiceErrorType errorType;
        String errorMsg;
        if (volleyError instanceof NoConnectionError) {
            errorType = EServiceErrorType.ERROR_NOT_CONNECTED;
            errorMsg = "无网络链接";
        } else if (volleyError instanceof TimeoutError) {
            errorType = EServiceErrorType.ERROR_TIMEOUT;
            errorMsg = "网络连接错误";
        } else {
            errorType = EServiceErrorType.ERROR_NONE;
            errorMsg = "网络连接错误";
        }
        serviceError.setErrorType(errorType);
        serviceError.setErrorMsg(errorMsg);
        return serviceError;
    }

}
