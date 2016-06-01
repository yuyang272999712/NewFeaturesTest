package com.yuyang.fitsystemwindowstestdrawer.googleWidget;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 很少出现但很实用的控件
 */
public class GoogleWidgetActivity extends AppCompatActivity {
    private Button timerButton;

    /**
     * TODO yuyang android提供的计时工具
     */
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_widget);

        findViews();
        initData();
        initAction();
    }

    private void initAction() {
        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.start();
            }
        });
    }

    private void findViews() {
        timerButton = (Button) findViewById(R.id.button);
    }

    private void initData() {
        //按键倒计时
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerButton.setClickable(false);
                timerButton.setText("倒计时" + millisUntilFinished/1000 + "秒");
            }

            @Override
            public void onFinish() {
                timerButton.setClickable(true);
                timerButton.setText("倒计时完成");
            }
        };
    }

    public void gotoFlexbox(View view){
        Intent intent = new Intent(this, FlexboxLayoutActivity.class);
        startActivity(intent);
    }
}
