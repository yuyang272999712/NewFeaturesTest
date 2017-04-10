package com.yuyang.volley_packaging;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

/**
 * Created by yuyang on 2017/4/10.
 */

public class RequestManager {
    private static final int DEFAULT_DISK_USAGE_BYTES = 10 * 1024 * 1024;//缓存空间大小10M

    private static volatile RequestManager mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private RequestManager(Context mContext) {
        this.mCtx = mContext;
        this.mRequestQueue = getRequestQueue();
    }

    public static RequestManager getInstance(Context context){
        if (mInstance == null){
            synchronized (RequestManager.class){
                if (mInstance == null){
                    mInstance = new RequestManager(context);
                }
            }
        }
        return mInstance;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /**
     * 清除缓存
     *
     * @param key key
     */
    public void clearRequestCache(String key) {
        if (key == null) return;
        getRequestQueue().getCache().remove(key);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            //初始化缓存
            Cache cache = new DiskBasedCache(mCtx.getCacheDir(), DEFAULT_DISK_USAGE_BYTES);
            //使用HttpURLConnection作为HTTP请求的客户端
            Network network = new BasicNetwork(new HurlStack());
            //初始化RequestQueue
            mRequestQueue = new RequestQueue(cache, network);
            //开始队列的循环
            mRequestQueue.start();
        }
        return mRequestQueue;
    }
}
