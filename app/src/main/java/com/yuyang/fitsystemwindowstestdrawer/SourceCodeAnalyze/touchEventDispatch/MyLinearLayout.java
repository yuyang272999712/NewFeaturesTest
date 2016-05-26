package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.touchEventDispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 自定义ViewGroup，重写所有与touch事件相关的方法，
 * 打印log，查看事件触发流程
 */
public class MyLinearLayout extends LinearLayout {
    private static final String TAG = MyLinearLayout.class.getSimpleName();

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(new OnTouchListener() {
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
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
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
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "---onInterceptTouchEvent ACTION_DOWN---");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "---onInterceptTouchEvent ACTION_MOVE---");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "---onInterceptTouchEvent ACTION_UP---");
                break;
        }
        return super.onInterceptTouchEvent(ev);
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

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Log.e(TAG, "requestDisallowInterceptTouchEvent ");
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }
}
