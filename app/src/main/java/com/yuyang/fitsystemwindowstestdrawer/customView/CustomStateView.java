package com.yuyang.fitsystemwindowstestdrawer.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 自定义控件状态
 * http://blog.csdn.net/imyfriend/article/details/8091476
 */
public class CustomStateView extends TextView {
    private static final int[] STATE_1 = {R.attr.state_1};
    private static final int[] STATE_2 = {R.attr.state_2};
    private static final int[] STATE_3 = {R.attr.state_3};

    private boolean state1 = false;
    private boolean state2 = false;
    private boolean state3 = false;

    public CustomStateView(Context context) {
        super(context);
    }

    public CustomStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomStateView);
        for (int i=0; i<a.length(); i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.CustomStateView_state_1:
                    state1 = a.getBoolean(attr, false);
                    break;
                case R.styleable.CustomStateView_state_2:
                    state2 = a.getBoolean(attr, false);
                    break;
                case R.styleable.CustomStateView_state_3:
                    state3 = a.getBoolean(attr, false);
                    break;
            }
        }
        a.recycle();
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace+3);
        if (state1){
            mergeDrawableStates(drawableState, STATE_1);
        }
        if (state2){
            mergeDrawableStates(drawableState, STATE_2);
        }
        if (state3){
            mergeDrawableStates(drawableState, STATE_3);
        }
        return drawableState;
    }

    public void setState1(boolean state1) {
        this.state1 = state1;
        state2 = false;
        state3 = false;
        refreshDrawableState();
    }

    public void setState2(boolean state2) {
        this.state2 = state2;
        state1 = false;
        state3 = false;
        refreshDrawableState();
    }

    public void setState3(boolean state3) {
        this.state3 = state3;
        state1 = false;
        state2 = false;
        refreshDrawableState();
    }
}
