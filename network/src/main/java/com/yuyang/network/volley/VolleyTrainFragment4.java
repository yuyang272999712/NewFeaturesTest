package com.yuyang.network.volley;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yuyang.network.R;
import com.yuyang.volley_packaging.base.PeonyError;
import com.yuyang.network.volley.baseView.BaseFragment;
import com.yuyang.volley_packaging.listener.OnReceivedDataListener;
import com.yuyang.volley_packaging.listener.OnReceivedErrorListener;
import com.yuyang.volley_packaging.utils.PeonyErrorUtil;

/**
 * Created by yuyang on 2017/4/6.
 */

public class VolleyTrainFragment4 extends BaseFragment {
    private VolleyTrainPresenter presenter;

    private TextView textView;
    private Button button;

    public static VolleyTrainFragment4 getInstance(){
        return new VolleyTrainFragment4();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        view.setBackgroundColor(Color.parseColor("#FFB082"));
        textView = (TextView) view.findViewById(R.id.result);
        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadUserInfo();
            }
        });
        presenter = new VolleyTrainPresenter(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void showUserInfo(User user) {
        textView.setText(user.toString());
    }
}
