package com.yuyang.network.volley;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.google.gson.Gson;
import com.yuyang.network.R;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by yuyang on 2017/4/6.
 */

public class VolleyTrainFragment3 extends Fragment {
    private static final int DEFAULT_DISK_USAGE_BYTES = 5*1024*1024;//缓存大小（默认也是5M）

    private TextView textView;

    RequestQueue queue;
    String url ="https://api.github.com/users/yuyang";
    Request request;

    public static VolleyTrainFragment3 getInstance(){
        return new VolleyTrainFragment3();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        view.setBackgroundColor(Color.parseColor("#82d7ff"));
        textView = (TextView) view.findViewById(R.id.result);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initMyQueue();
        startMyRequest();
    }

    private void startMyRequest() {
        //发送请求
        request = new MyRequest(User.class, url,
                new Response.Listener<User>() {
                    @Override
                    public void onResponse(User response) {
                        textView.setText(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("That didn't work!");
                    }
                });
        request.setTag(this);//用于取消请求用
        queue.add(request);
    }

    private void initMyQueue() {
        //初始化缓存
        Cache cache = new DiskBasedCache(getContext().getCacheDir(), DEFAULT_DISK_USAGE_BYTES);
        //使用HttpURLConnection作为HTTP请求的客户端
        Network network = new BasicNetwork(new HurlStack());
        //初始化RequestQueue
        queue = new RequestQueue(cache, network);
        //开始队列的循环
        queue.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消请求
        if (queue != null) {
            queue.cancelAll(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        queue.stop();
    }

    private class MyRequest<T> extends Request<T>{
        private String URL;
        private final Class<T> clazz;
        private final Response.Listener<T> mListener;
        private Gson gson = new Gson();

        public MyRequest(Class<T> clazz, String url, Response.Listener<T> listener, Response.ErrorListener errorListener) {
            super(Method.GET, url, errorListener);
            this.URL = url;
            this.clazz = clazz;
            this.mListener = listener;
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> headers = super.getHeaders();
            return super.getHeaders();
        }

        @Override
        protected Response<T> parseNetworkResponse(NetworkResponse response) {
            try {
                String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                return Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return Response.error(new ParseError(e));
            }
        }

        @Override
        protected void deliverResponse(T response) {
            mListener.onResponse(response);
        }
    }
}
