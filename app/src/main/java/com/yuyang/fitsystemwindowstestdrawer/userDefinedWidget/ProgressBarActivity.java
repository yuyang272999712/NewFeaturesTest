package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.progressBars.HorizontalProgressBarWithNumber;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.progressBars.RoundProgressBarWidthNumber;

/**
 * 自定义ProgressBar
 *
 * 系统ProgressBar
     ProgressBar的关键属性 
        android:max=”100” - ---最大显示进度 
        android:progress=”50”----第一显示进度 
        android:secondaryProgress=”80”---第二显示进度 
        android:indeterminateOnly=”true”---设置是否精确显示 Ps：true：不精确显示进度  false：精确显示进度 
        android:indeterminateDrawable="@drawable/*" ---不精确显示进度时的进度条样式
        android:progressDrawable="@drawable/*" ---精确显示进度时的进度条样式
    --------------------------------------------------------------- 
     ProgressBar的关键方法 
         1. setProgress(int) 设置第一进度 
         2. setSecondaryProgress(int) 设置第二进度 
         3. getProgress() 获取第一进度 
         4. getSecondaryProgress() 获取第二进度 
         5. incrementProgressBy(int) 增加或减少第一进度 
         6. incrementSecondaryProgressBy(int) 增加或减少第二进度 7. getMax() 获取最大进度
 */
public class ProgressBarActivity extends AppCompatActivity {
    private ProgressBar progressBar5;
    private ProgressBar progressBar6;
    private HorizontalProgressBarWithNumber myHorProgressBar1;
    private HorizontalProgressBarWithNumber myHorProgressBar2;
    private HorizontalProgressBarWithNumber myHorProgressBar3;
    private RoundProgressBarWidthNumber myRoundProgressBar1;
    private RoundProgressBarWidthNumber myRoundProgressBar2;

    private Button start;

    private static final int MSG_PROGRESS_UPDATE = 0x110;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int progress = progressBar5.getProgress();
            updateProgress(++progress);
            if (progress >= 100) {
                mHandler.removeMessages(MSG_PROGRESS_UPDATE);
                return;
            }
            mHandler.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE, 100);
        }
    };

    private void updateProgress(int progress){
        progressBar5.setProgress(progress);
        progressBar6.setProgress(progress);
        myHorProgressBar1.setProgress(progress);
        myHorProgressBar2.setProgress(progress);
        myHorProgressBar3.setProgress(progress);
        myRoundProgressBar1.setProgress(progress);
        myRoundProgressBar2.setProgress(progress);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);

        findViews();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE);
            }
        });
    }

    private void findViews() {
        progressBar5 = (ProgressBar) findViewById(R.id.progress_system_5);
        progressBar6 = (ProgressBar) findViewById(R.id.progress_system_6);
        myHorProgressBar1 = (HorizontalProgressBarWithNumber) findViewById(R.id.progress_horizontal_1);
        myHorProgressBar2 = (HorizontalProgressBarWithNumber) findViewById(R.id.progress_horizontal_2);
        myHorProgressBar3 = (HorizontalProgressBarWithNumber) findViewById(R.id.progress_horizontal_3);
        myRoundProgressBar1 = (RoundProgressBarWidthNumber) findViewById(R.id.progress_round_1);
        myRoundProgressBar2 = (RoundProgressBarWidthNumber) findViewById(R.id.progress_round_2);

        start = (Button) findViewById(R.id.progress_start);
    }
}
