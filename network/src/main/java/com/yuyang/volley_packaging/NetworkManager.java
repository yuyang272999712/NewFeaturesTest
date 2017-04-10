package com.yuyang.volley_packaging;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.yuyang.volley_packaging.base.EndpointRequest;
import com.yuyang.volley_packaging.base.PeonyError;
import com.yuyang.volley_packaging.base.PeonyRequest;
import com.yuyang.volley_packaging.listener.OnLoadCompleteListener;
import com.yuyang.volley_packaging.listener.OnReceivedDataListener;
import com.yuyang.volley_packaging.listener.OnReceivedErrorListener;
import com.yuyang.volley_packaging.listener.OnRequestProcessLister;

/**
 * Created by yuyang on 2017/4/10.
 */

public class NetworkManager {
    private static final int TIME_OUT = 3500;//请求超时时间
    private Context mContext;
    private static volatile NetworkManager mInstance;

    private NetworkManager(Context mContext){
        this.mContext = mContext;
    }

    public static synchronized NetworkManager getInstance(@NonNull Context mContext){
        if (mInstance == null){
            synchronized (NetworkManager.class){
                if (mInstance == null){
                    mInstance = new NetworkManager(mContext);
                }
            }
        }
        return mInstance;
    }

    public <T> void loadData(@NonNull String path,
                             @NonNull Object requestTag,
                             @NonNull EndpointRequest request,
                             @NonNull Class<T> responseClass,
                             @NonNull final OnReceivedDataListener<T> listener,
                             @NonNull final OnReceivedErrorListener errorListener,
                             final OnLoadCompleteListener onLoadCompleteListener,
                             OnRequestProcessLister processLister){
        String url = request.getProtocol() + "://" + request.getHost();
        /*if (!TextUtils.isEmpty(request.getPort()+"")){//!--yuyang 实际开发中应该是有端口号的
            url = url + ":" + request.getPort();
        }*/
        if (!TextUtils.isEmpty(request.getPath())) {
            url = url + request.getPath() + path;
        } else {
            url = url + path;
        }

        loadData(url,
                String.valueOf(requestTag),
                false,//不使用缓存
                null,
                0,
                0,
                request,
                responseClass,
                new Response.Listener<T>() {
                    @Override
                    public void onResponse(T response) {
                        if (onLoadCompleteListener != null){
                            onLoadCompleteListener.onLoadComplete();
                        }
                        listener.onReceivedData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (onLoadCompleteListener != null){
                            onLoadCompleteListener.onLoadComplete();
                        }
                        PeonyError peonyError = new PeonyError(error);
                        if (error instanceof NoConnectionError) {
                            peonyError.setErrorType(PeonyError.ERROR_NO_CONNECTION);
                        } else if (error instanceof ServerError) {
                            peonyError.setErrorType(PeonyError.ERROR_SERVER);
                        } else if (error instanceof TimeoutError) {
                            peonyError.setErrorType(PeonyError.ERROR_TIMEOUT);
                        } else if (error instanceof ParseError) {
                            peonyError.setErrorType(PeonyError.ERROR_PARSE);
                        }
                        errorListener.onReceivedError(peonyError);
                    }
                },
                processLister);
    }

    /**
     * 总服务基础入口
     */
    public <T> void loadData(String url,
                             String token,
                             boolean cache,
                             String cacheKey,
                             long cacheDuration,
                             long refreshDuration,
                             EndpointRequest request,
                             Class<T> responseClass,
                             Response.Listener<T> successListener,
                             Response.ErrorListener errorListener,
                             OnRequestProcessLister processLister) {
        PeonyRequest<T> peonyRequest =
                new PeonyRequest<>(url, request, responseClass, successListener, errorListener,
                        processLister);
        peonyRequest.setTag(token);
        peonyRequest.setShouldCache(cache);
        peonyRequest.setCacheControl(cacheKey, cacheDuration, refreshDuration);
        peonyRequest.setCustomRetryPolicy(//设置请求超时时间和重试次数
                new DefaultRetryPolicy(TIME_OUT, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestManager.getInstance(mContext.getApplicationContext()).addToRequestQueue(peonyRequest);
    }

    public void cancel(Object requestTag){
        RequestManager.getInstance(mContext.getApplicationContext())
                .getRequestQueue()
                .cancelAll(requestTag);
    }
}
