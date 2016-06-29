package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.singleItemScrollView;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 无限循环竖直方向上的滚动
 */
public class SingleItemScrollActivity extends AppCompatActivity {
    private SingleItemScrollView mScrollView;
    private SingleItemScrollView.Adapter mAdapter;
    private LayoutInflater mInflater;
    private List<Bean> mDatas = new ArrayList<Bean>(Arrays.asList(//
            new Bean(R.mipmap.wechat_icon_1, Color.rgb(123, 34, 45)),//
            new Bean(R.mipmap.wechat_icon_2, Color.rgb(12, 34, 145)),//
            new Bean(R.mipmap.wechat_icon_3, Color.rgb(177, 234, 145)),//
            new Bean(R.mipmap.wechat_icon_4, Color.rgb(88, 134, 145))//
    ));

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_scroll);

        mInflater = LayoutInflater.from(this);

        mScrollView = (SingleItemScrollView) findViewById(R.id.id_scrollview);
        mAdapter = new SingleItemScrollView.Adapter() {

            @Override
            public View getView(SingleItemScrollView parent, int pos) {
                View view = mInflater.inflate(R.layout.item_single_scroll, null);
                ImageView iv = (ImageView) view.findViewById(R.id.id_title);
                iv.setImageResource(mDatas.get(pos).getResId());
                view.setBackgroundColor(mDatas.get(pos).getColor());
                return view;
            }

            @Override
            public int getCount() {
                return 4;
            }

        };

        mScrollView.setAdapter(mAdapter);

        mScrollView.setOnItemClickListener(new SingleItemScrollView.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View view) {
                Toast.makeText(getApplicationContext(), pos + "",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
