package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.touchEventDispatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * View的事件转发的流程（参考：http://blog.csdn.net/lmj623565791/article/details/38960443）
 *
 *  不管是DOWN，MOVE，UP都会按照下面的顺序执行：
 *      1、dispatchTouchEvent
 *      2、setOnTouchListener 的 onTouch
 *      3、onTouchEvent
 *      4、setOnClickListener 的 onClick
 *
 */
public class ViewTouchEventActivity extends AppCompatActivity {
    private static final String TAG = "ViewTouchEventActivity";
    private MyButton myButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_touch_event);

        findViews();
        setAction();
    }

    private void setAction() {

        myButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        Log.e(TAG, "---onTouch ACTION_DOWN---");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e(TAG, "---onTouch ACTION_MOVE---");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e(TAG, "---onTouch ACTION_UP---");
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "---onClick---");
            }
        });
        myButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e(TAG, "---onLongClick---");
                //TODO yuyang 如果这里返回false，onClick事件会继续被触发
                return false;
            }
        });
    }

    private void findViews() {
        myButton = (MyButton) findViewById(R.id.view_touch_my_btn);
    }
}
