package com.yuyang.fitsystemwindowstestdrawer.eventBus.myEventBusSimple;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.eventBus.myEventBus.MyEventBus;
import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 注册EventBus事件监听
 */
public class ItemDetailFragment extends Fragment {
    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyEventBus.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyEventBus.getInstance().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test, container, false);
        textView = (TextView) rootView.findViewById(R.id.fragment_test_text);
        return rootView;
    }

    public void onEventUI(Item item){
        if (item != null){
            textView.setText(item.toString());
        }
    }
}
