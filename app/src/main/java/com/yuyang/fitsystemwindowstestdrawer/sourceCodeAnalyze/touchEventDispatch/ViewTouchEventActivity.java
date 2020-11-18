package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.touchEventDispatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.touchEventDispatch.eventConflict_1.EventConflictActivity1;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.touchEventDispatch.eventConflict_2.EventConflictActivity2;

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
                        //return true; //TODO yuyang 如果在onTouch中的DOWN事件返回了true，那么接下来的所有事件onTouchEvent都接受不到
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e(TAG, "---onTouch ACTION_MOVE---");
                        //return true; //TODO yuyang 如果在onTouch中的MOVE事件返回了true，那么接下来的onTouchEvent中的MOVE事件不会被触发
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e(TAG, "---onTouch ACTION_UP---");
                        /*
                         TODO yuyang 如果在onTouch中的UP事件返回了true，那么接下来的onTouchEvent中的UP事件不会被触发
                          还有一种特殊情况，如果在onTouch中未拦截DOWN事件，那么onTouchEvent中接收到了DOWN事件，
                          但是后续的UP事件接受不到，此时就会认为是发生了！！！长按事件！！！
                         */
                        //return true;
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * TODO yuyang 此方法是activity的方法，当此activity在栈顶时，触屏点击按home，back，menu键等都会触发此方法。
     * 下拉statubar、旋转屏幕、锁屏不会触发此方法。所以它会用在屏保应用上，因为当你触屏机器 就会立马触发一个事件，
     * 而这个事件又不太明确是什么，正好屏保满足此需求；或者对于一个Activity，控制多长时间没有用户点响应的时候，
     * 自己消失等。
     */
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /**
     * 事件冲突处理-外部拦截法
     * @param view
     */
    public void gotoEventConflict1(View view){
        Intent intent = new Intent(this, EventConflictActivity1.class);
        startActivity(intent);
    }

    /**
     * 事件冲突处理-外部拦截法
     * @param view
     */
    public void gotoEventConflict2(View view){
        Intent intent = new Intent(this, EventConflictActivity2.class);
        startActivity(intent);
    }

}
