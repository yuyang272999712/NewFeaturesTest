package com.yuyang.fitsystemwindowstestdrawer.destWidget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 2016/4/28.
 * 更新桌面小控件
 */
public class DestWidgetActivity extends AppCompatActivity {
    Button sendBroadCast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dest_widget);

        sendBroadCast = (Button) findViewById(R.id.dest_send_broadcast);

        sendBroadCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DestWidget.TIME_CHANGE);
                sendBroadcast(intent);
            }
        });
    }
}
