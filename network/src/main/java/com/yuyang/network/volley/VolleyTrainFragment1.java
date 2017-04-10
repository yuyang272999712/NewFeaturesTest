package com.yuyang.network.volley;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yuyang.network.R;

/**
 * Created by yuyang on 2017/4/6.
 */

public class VolleyTrainFragment1 extends Fragment {
    private TextView textView;

    RequestQueue queue;
    String url ="https://www.baidu.com";
    StringRequest stringRequest;

    public static VolleyTrainFragment1 getInstance(){
        return new VolleyTrainFragment1();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        view.setBackgroundColor(Color.parseColor("#ff82ff"));
        textView = (TextView) view.findViewById(R.id.result);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //发送请求
        queue = Volley.newRequestQueue(getContext());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消请求
        if (queue != null) {
            queue.cancelAll(this);
        }
    }
}
