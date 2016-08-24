package com.yuyang.fitsystemwindowstestdrawer.internetAbout.okHttpAbout;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 拦截器，这个拦截器针对getRequest进行修改
 */
public class GetRequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest = null;
        //如果时GET请求
        if ("GET".equals(request.method())){
            newRequest = request.newBuilder().url("http://www.baidu.com/s?wd=于洋").get().build();
        }else {
            newRequest = request;
        }
        //TODO yuyang 对于OkHttp的这句话非常重要，在这里执行网络请求
        Response response = chain.proceed(newRequest);
        return response;
    }
}
