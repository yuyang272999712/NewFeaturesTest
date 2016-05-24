package com.yuyang.fitsystemwindowstestdrawer.userDefinedViews;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedViews.gestureLock.GestureLockViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 手势密码
 */
public class GestureLockActivity extends AppCompatActivity {
    private GestureLockViewGroup lockViewGroup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_lock);

        List<Integer> answer = new ArrayList<>();
        answer.add(1);
        answer.add(2);
        answer.add(3);
        answer.add(6);
        answer.add(9);
        lockViewGroup = (GestureLockViewGroup) findViewById(R.id.gesture_view);
        lockViewGroup.setStatus(GestureLockViewGroup.Mode.STATUS_CHECK);
        lockViewGroup.setAnswer(answer);
        lockViewGroup.setOnGestureLockViewListener(new GestureLockViewGroup.OnGestureLockViewListener() {
            @Override
            public void onBlockSelected(int cId) {
//                Toast.makeText(GestureLockActivity.this, "选中了第"+cId+"个", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGestureEvent(boolean matched) {
                Toast.makeText(GestureLockActivity.this, matched+"", Toast.LENGTH_SHORT).show();
                Handler handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        lockViewGroup.clearGesture();
                        return false;
                    }
                });
                handler.sendEmptyMessageDelayed(0, 2000);
            }

            @Override
            public void onUnmatchedExceedBoundary() {
                Toast.makeText(GestureLockActivity.this, "错误5次...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void setGestureResult(List<Integer> result) {
                Toast.makeText(GestureLockActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                Handler handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        lockViewGroup.clearGesture();
                        return false;
                    }
                });
                handler.sendEmptyMessageDelayed(0, 2000);
            }

            @Override
            public void setGestureError() {
                Handler handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        lockViewGroup.clearGesture();
                        return false;
                    }
                });
                handler.sendEmptyMessageDelayed(0, 2000);
            }

        });
    }
}
