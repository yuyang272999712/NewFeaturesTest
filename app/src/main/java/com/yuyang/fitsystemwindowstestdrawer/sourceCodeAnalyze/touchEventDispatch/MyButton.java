package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.touchEventDispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * 自定义View，重写所有与touch事件相关的方法，
 * 打印log，查看事件触发流程
 */
public class MyButton extends Button {
    private static final String TAG = MyButton.class.getSimpleName();

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "---dispatchTouchEvent ACTION_DOWN---");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "---dispatchTouchEvent ACTION_MOVE---");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "---dispatchTouchEvent ACTION_UP---");
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "---onTouchEvent ACTION_DOWN---");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "---onTouchEvent ACTION_MOVE---");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "---onTouchEvent ACTION_UP---");
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        //TODO yuyang 在onTouchEvent的ACTION_UP事件后执行
        //这里可以执行setOnClickListener的onClick事件，播放按键音，执行无障碍操作等
        Log.e(TAG, "---performClick---");
        return super.performClick();
    }
}
