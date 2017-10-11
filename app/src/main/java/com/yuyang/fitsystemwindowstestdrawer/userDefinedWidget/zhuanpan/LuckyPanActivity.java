package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.zhuanpan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.zhuanpan.LuckyPanView;

/**
 * 抽奖活动实例
 */
public class LuckyPanActivity extends AppCompatActivity {
    private LuckyPanView mLuckyPanView;
    private ImageView mStartBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_pan);

        mLuckyPanView = (LuckyPanView) findViewById(R.id.id_luckypan);
        mStartBtn = (ImageView) findViewById(R.id.id_start_btn);

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mLuckyPanView.isStart()) {
                    mStartBtn.setImageResource(R.mipmap.zhuanpan_stop);
                    mLuckyPanView.luckyStart(4);
                } else {
                    if (!mLuckyPanView.isShouldEnd()) {
                        mStartBtn.setImageResource(R.mipmap.zhuanpan_start);
                        mLuckyPanView.luckyEnd();
                    }
                }
            }
        });
    }
}
