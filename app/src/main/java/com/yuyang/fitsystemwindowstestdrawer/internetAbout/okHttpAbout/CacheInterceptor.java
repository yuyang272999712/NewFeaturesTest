package com.yuyang.fitsystemwindowstestdrawer.internetAbout.okHttpAbout;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 缓存拦截器，如果服务器不支持缓存，需要使用这个拦截器通过修改response头信息来支持缓存
 */
public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        Response response1 = response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                //cache for 1 minute
                .header("Cache-Control", "max-age=" + 60*1)
                .build();

        return response1;
    }
}
