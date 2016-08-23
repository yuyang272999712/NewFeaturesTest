package com.yuyang.fitsystemwindowstestdrawer.internetAbout.okHttpAbout;

import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.*;
import okhttp3.Callback;

/**
 * 简单模拟post请求
 */
public class OkHttpUtils {
    private static volatile OkHttpClient okHttpClient;

    private static OkHttpClient getClient(){
        if (okHttpClient == null){
            synchronized (OkHttpUtils.class){
                if (okHttpClient == null){
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return okHttpClient;
    }

    public static void postParams(String url, Map<String, String> params, final CallbackBase<String> callBack){
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        for (String key:params.keySet()){
            bodyBuilder.add(key, params.get(key).toString());
        }
        Request.Builder builder = new Request.Builder();
        Request request = builder
                .url(url)
                .post(bodyBuilder.build())
                .tag(url)//TODO 可以通过tag取消请求，call.cancel();
                .build();
        Call call = getClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("OkHttp请求","请求失败");
                callBack.onError(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("OkHttp请求",response.body().string());
                try {
                    callBack.parseNetworkResponse(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 通过tag取消请求
     * @param tag
     */
    public static void cancelTag(Object tag) {
        for (Call call : getClient().dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : getClient().dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }
}
