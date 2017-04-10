package com.yuyang.network.volley;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.yuyang.network.R;

/**
 * Created by yuyang on 2017/4/6.
 */

public class VolleyTrainFragment2 extends Fragment {
    private static final int DEFAULT_DISK_USAGE_BYTES = 5*1024*1024;//缓存大小（默认也是5M）

    private TextView textView;

    RequestQueue queue;
    String url ="https://www.baidu.com";
    StringRequest stringRequest;

    public static VolleyTrainFragment2 getInstance(){
        return new VolleyTrainFragment2();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        view.setBackgroundColor(Color.parseColor("#f0ff82"));
        textView = (TextView) view.findViewById(R.id.result);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initMyQueue();
        startRequest();
    }

    private void startRequest() {
        //发送请求
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("That didn't work!");
                    }
                });
        stringRequest.setTag(this);//用于取消请求用
        queue.add(stringRequest);
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
}
