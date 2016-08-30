package com.yuyang.fitsystemwindowstestdrawer.internetAbout.okHttpAbout;

import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * TODO yuyang OkHttp中的get、post请求模拟，上传、下载模拟
 */
public class OkHttpUtils {
    private static Handler handler = new Handler();
    //10M缓存
    private static Cache cache = new Cache(new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+File.separator+"OkHttCache"), 10*1024*1024);
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            //.addInterceptor(new GetRequestInterceptor())//TODO yuyang 这里添加了一个拦截器
            .addInterceptor(new CacheInterceptor())//TODO yuyang 使百度支持缓存
            .cache(cache)//TODO yuyang OkHttp默认不使用缓存
            .build();

    /**
     * post请求
     * @param url
     * @param params
     * @param callBack
     */
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
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("OkHttp请求","请求失败");
                        callBack.onError(call, e);
                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                try {
                    final String str = callBack.parseNetworkResponse(response);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("OkHttp请求",str);
                            callBack.onResponse(str);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getParams(String url, Map<String, String> params, final CallbackBase<String> callBack){
        Uri.Builder uriBuilder = Uri.parse(url).buildUpon();
        for (String key:params.keySet()){
            uriBuilder.appendQueryParameter(key, params.get(key));
        }
        String newUrl = uriBuilder.build().toString();

        Request request = new Request.Builder()
                .url(newUrl)
                .tag(newUrl)
                .get()
                //.cacheControl(CacheControl.FORCE_NETWORK)//TODO yuyang OkHttp设置缓存使用机制，强制从网络／强制从缓存
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("OkHttp请求","请求失败");
                        callBack.onError(call, e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String str = callBack.parseNetworkResponse(response);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("OkHttp请求",str);
                            callBack.onResponse(str);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 上传文件
     * @param url
     * @param params
     * @param file
     * @param callBack
     */
    public static void uploadFile(String url, Map<String, String> params, File file, final CallbackBase<String> callBack) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//TODO yuyang 多参上传使用MultipartBody，MultipartBody.FORM="multipart/form-data"
        String media_type = guessMimeType(file.getName());
        MediaType mediaType = MediaType.parse(media_type);//TODO yuyang 规定上传文件类型
        RequestBody fileBody = RequestBody.create(mediaType, file);//每个文件对应一个RequestBody，如果只有一个文件上传可以直接post这个RequestBody
        builder.addFormDataPart("image", file.getName(), fileBody);//将所有的RequestBody加入到MultipartBody中
        for (String key:params.keySet()){
            builder.addFormDataPart(key, params.get(key));
        }
        RequestBody requestBody = builder.build();
        RequestBody wrappedRequestBody = wrapRequestBody(requestBody, callBack);

        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "d97763c63d28748061e431b76b1f21d071471415644")
                .url(url)
                .tag(url)
                .post(wrappedRequestBody)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("OkHttp请求","上传失败"+e.getMessage());
                        callBack.onError(call, e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String str = callBack.parseNetworkResponse(response);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("OkHttp请求",str);
                            callBack.onResponse(str);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 上传／下载进度回调监听
     * @param requestBody
     * @param callback
     * @return
     */
    private static RequestBody wrapRequestBody(RequestBody requestBody, final CallbackBase callback) {
        if (callback == null) return requestBody;
        CountingRequestBody countingRequestBody = new CountingRequestBody(requestBody, new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(final long bytesWritten, final long contentLength) {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.inProgress(bytesWritten * 1.0f / contentLength,contentLength);
                    }
                });

            }
        });
        return countingRequestBody;
    }

    /**
     * TODO yuyang 获取上传文件的文件类型
     * @param path
     * @return
     */
    private static String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     * 下载文件
     * @param url
     * @param callBack
     */
    public static void downFile(String url, final CallbackBase<File> callBack) {
        Request request = new Request.Builder().url(url).tag(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("OkHttp请求","请求失败");
                        callBack.onError(call, e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final File file = callBack.parseNetworkResponse(response);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("OkHttp请求","下载文件成功");
                            callBack.onResponse(file);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    onFailure(call, (IOException) e);
                }
            }
        });
    }

    /**
     * 通过tag取消请求
     * @param tag
     */
    public static void cancelTag(Object tag) {
        for (Call call : okHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : okHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }
}
