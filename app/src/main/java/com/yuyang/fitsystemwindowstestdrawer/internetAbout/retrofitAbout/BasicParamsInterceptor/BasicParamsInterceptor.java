package com.yuyang.fitsystemwindowstestdrawer.internetAbout.retrofitAbout.BasicParamsInterceptor;

import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * okhttp3的拦截器，该类用于向请求参数中添加公共参数，如：用户ID，Session，apk版本号等
 */
public class BasicParamsInterceptor implements Interceptor {
    Map<String, String> queryParamsMap = new HashMap<>();
    Map<String, String> paramsMap = new HashMap<>();
    Map<String, String> headerParamsMap = new HashMap<>();
    List<String> headerLinesList = new ArrayList<>();

    private BasicParamsInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        // process header params inject
        Headers.Builder headerBuilder = request.headers().newBuilder();
        if (headerParamsMap.size() > 0) {
            Iterator iterator = headerParamsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                headerBuilder.add((String) entry.getKey(), (String) entry.getValue());
            }
        }

        if (headerLinesList.size() > 0) {
            for (String line: headerLinesList) {
                headerBuilder.add(line);
            }
        }

        requestBuilder.headers(headerBuilder.build());
        // process header params end


        // process queryParams inject whatever it's GET or POST
        if (queryParamsMap.size() > 0) {
            injectParamsIntoUrl(request, requestBuilder, queryParamsMap);
        }
        // process header params end


        // process post body inject
        if (canInjectIntoBody(request)) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            if (paramsMap.size() > 0) {
                Iterator iterator = paramsMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    formBodyBuilder.add((String) entry.getKey(), (String) entry.getValue());
                }
            }
            RequestBody formBody = formBodyBuilder.build();
            String postBodyString = bodyToString(request.body());
            postBodyString += ((postBodyString.length() > 0) ? "&" : "") +  bodyToString(formBody);
            requestBuilder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString));
        } else {    // can't inject into body, then inject into url
            injectParamsIntoUrl(request, requestBuilder, paramsMap);
        }

        request = requestBuilder.build();
        return chain.proceed(request);
    }

    private boolean canInjectIntoBody(Request request) {
        if (request == null) {
            return false;
        }
        if (!TextUtils.equals(request.method(), "POST")) {
            return false;
        }
        RequestBody body = request.body();
        if (body == null) {
            return false;
        }
        MediaType mediaType = body.contentType();
        if (mediaType == null) {
            return false;
        }
        if (!TextUtils.equals(mediaType.subtype(), "x-www-form-urlencoded")) {
            return false;
        }
        return true;
    }

    // func to inject params into url
    private void injectParamsIntoUrl(Request request, Request.Builder requestBuilder, Map<String, String> paramsMap) {
        HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
        if (paramsMap.size() > 0) {
            Iterator iterator = paramsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                httpUrlBuilder.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
            }
        }

        requestBuilder.url(httpUrlBuilder.build());
    }

    private static String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if(copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }

    public static class Builder {

        BasicParamsInterceptor interceptor;

        public Builder() {
            interceptor = new BasicParamsInterceptor();
        }

        /**
         * post请求，且body type为x-www-form-urlencoded时，键值对公共参数插入到 body 参数中，
         * 其他情况插入到 url query 参数中。
         * @param key
         * @param value
         * @return
         */
        public Builder addParam(String key, String value) {
            interceptor.paramsMap.put(key, value);
            return this;
        }

        /**
         * 同上，不过这里用键值对 Map 作为参数批量插入。
         * @param paramsMap
         * @return
         */
        public Builder addParamsMap(Map<String, String> paramsMap) {
            interceptor.paramsMap.putAll(paramsMap);
            return this;
        }

        /**
         * 插入键值对参数到url query中,相当于Get请求的公共参数。
         * @param key
         * @param value
         * @return
         */
        public Builder addQueryParam(String key, String value) {
            interceptor.queryParamsMap.put(key, value);
            return this;
        }

        /**
         * 插入键值对参数 map 到 url query 中，批量插入。
         * @param queryParamsMap
         * @return
         */
        public Builder addQueryParamsMap(Map<String, String> queryParamsMap) {
            interceptor.queryParamsMap.putAll(queryParamsMap);
            return this;
        }

        /**
         * 在 header 中插入键值对参数。
         * @param key
         * @param value
         * @return
         */
        public Builder addHeaderParam(String key, String value) {
            interceptor.headerParamsMap.put(key, value);
            return this;
        }

        /**
         * 在 header 中插入键值对 Map 集合，批量插入。
         * @param headerParamsMap
         * @return
         */
        public Builder addHeaderParamsMap(Map<String, String> headerParamsMap) {
            interceptor.headerParamsMap.putAll(headerParamsMap);
            return this;
        }

        /**
         * 在header中插入headerLine字符串，字符串需要符合 -1 != headerLine.indexOf(“:”) 的规则，
         * 即可以解析成键值对。
         * @param headerLine
         * @return
         */
        public Builder addHeaderLine(String headerLine) {
            int index = headerLine.indexOf(":");
            if (index == -1) {
                throw new IllegalArgumentException("Unexpected header: " + headerLine);
            }
            interceptor.headerLinesList.add(headerLine);
            return this;
        }

        /**
         * 同上，headerLineList: List 为参数，批量插入 headerLine。
         * @param headerLinesList
         * @return
         */
        public Builder addHeaderLinesList(List<String> headerLinesList) {
            for (String headerLine: headerLinesList) {
                int index = headerLine.indexOf(":");
                if (index == -1) {
                    throw new IllegalArgumentException("Unexpected header: " + headerLine);
                }
                interceptor.headerLinesList.add(headerLine);
            }
            return this;
        }

        public BasicParamsInterceptor build() {
            return interceptor;
        }

    }
}
