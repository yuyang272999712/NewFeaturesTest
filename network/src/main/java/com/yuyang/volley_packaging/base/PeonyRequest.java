package com.yuyang.volley_packaging.base;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.yuyang.volley_packaging.listener.OnRequestProcessLister;
import com.yuyang.volley_packaging.utils.NetWorkLog;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * 自定义请求以便返回想要的类型<T>
 */

public class PeonyRequest<T> extends Request<T> {
    public final static String TAG = PeonyRequest.class.getSimpleName();

    private final Class<T> clazz;
    private final String mUrl;
    private final EndpointRequest mRequestObject;
    private final Response.Listener<T> mListener;
    private final OnRequestProcessLister mProcessListener;//进度回调
    //缓存配置
    private String mCacheKey;
    private long mCacheDuration;
    private long mRefreshDuration;

    private final Gson gson = new Gson();

    public PeonyRequest(String url, EndpointRequest requestObject, Class<T> clazz,
                        Response.Listener<T> listener, Response.ErrorListener errorListener,
                        OnRequestProcessLister processLister) {
        super(Method.GET, url, errorListener);//!--yuyang 这里因为使用的是gitHub的接口，都是GET请求方式的
        this.mUrl = url;
        this.clazz =  clazz;
        this.mRequestObject = requestObject;
        this.mListener = listener;
        this.mProcessListener = processLister;

        //to avoid duplicate request
        RetryPolicy mRetryPolicy = new DefaultRetryPolicy(
                2 * 60 * 1000,//请求超时时间
                0,//重试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        setRetryPolicy(mRetryPolicy);
        setShouldCache(false);//不缓存
    }

    /**
     * 设置缓存key和时间
     * @param mCacheKey mCacheKey
     * @param mCacheDuration mCacheDuration
     * @param mRefreshDuration mRefreshDuration
     */
    public void setCacheControl(String mCacheKey, long mCacheDuration, long mRefreshDuration) {
        this.mCacheKey = mCacheKey;
        this.mCacheDuration = mCacheDuration;
        this.mRefreshDuration = mRefreshDuration;
    }

    @Override
    public String getCacheKey() {
        return mCacheKey == null ? super.getCacheKey() : mCacheKey;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = mRequestObject.getHeaders();
        if (headers == null){
            headers = super.getHeaders();
        }
        return headers;
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=" + getParamsEncoding();
    }

    //!--yuyang POST请求时的body数据
    @Override
    public byte[] getBody() throws AuthFailureError {
        if (mRequestObject == null)
            return null;
        String json = gson.toJson(mRefreshDuration);//将请求实体转换为json
        showJSON(mRequestObject.isShowJSON(), mUrl, "Request", json);
        return json.getBytes();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        NetWorkLog.v(response.headers.get("X-RateLimit-Limit"));//!--yuyang 我只是想看下返回header里的值
        String json = getJsonString(response.data, HttpHeaderParser.parseCharset(response.headers));
        showJSON(mRequestObject.isShowJSON(), mUrl, "Response", json);
        T result = gson.fromJson(json, clazz);
        return Response.success(result, getCacheEntry(response));
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    /**
     * 自定义超时时间、重新请求次数
     * @param customRetryPolicy
     */
    public void setCustomRetryPolicy(RetryPolicy customRetryPolicy) {
        setRetryPolicy(customRetryPolicy);
    }

    public Cache.Entry getCacheEntry(NetworkResponse response) {
        Map<String, String> headers = response.headers;

        Cache.Entry entry = new Cache.Entry();
        entry.data = response.data;
        entry.responseHeaders = headers;

        long currentTime = System.currentTimeMillis();
        entry.ttl = mCacheDuration == -1 ? Long.MAX_VALUE : currentTime + mCacheDuration;
        entry.softTtl = mRefreshDuration == -1 ? Long.MAX_VALUE : currentTime + mRefreshDuration;
        return entry;
    }

    /**
     * 获取Json字符串
     */
    private String getJsonString(byte[] data, String charsetName) {
        StringBuilder sb = new StringBuilder();
        if (data != null && data.length > 1) {
            byte[] h = new byte[2];
            h[0] = data[0];
            h[1] = data[1];
            int head = (h[0] << 8) | (h[1] & 0xFF);
            boolean t = head == 0x1f8b; // 是否压缩

            InputStream inputStream = null;
            BufferedReader bufferedReader = null;
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(data);
                if (t) { // 压缩
                    inputStream = new GZIPInputStream(bis);
                } else {// 非压缩
                    inputStream = bis;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charsetName));
                for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static void showJSON(boolean isShowJSON, String url, String tag, String message) {
        if (!isShowJSON)
            return;
        if (!TextUtils.isEmpty(url))
            NetWorkLog.e("@@@@@@-url: " + url);
        if (TextUtils.isEmpty(tag))
            tag = " ";
        NetWorkLog.e("@@@@@@- Begin " + tag + " Body -@@@@@@");
        NetWorkLog.e(message);
        NetWorkLog.e("@@@@@@- End " + tag + " Body -@@@@@@");
    }
}
