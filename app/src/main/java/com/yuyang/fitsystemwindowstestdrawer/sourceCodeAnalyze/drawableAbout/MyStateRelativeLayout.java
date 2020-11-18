package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.drawableAbout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 自定义Drawable State
 */
public class MyStateRelativeLayout extends RelativeLayout {
    private static final int[] STATE_MESSAGE_READED = {R.attr.state_message_readed};
    private boolean mMessageReaded = false;

    public MyStateRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if (mMessageReaded){
            int[] drawableState = super.onCreateDrawableState(extraSpace+1);
            mergeDrawableStates(drawableState, STATE_MESSAGE_READED);
            return drawableState;
        }
        return super.onCreateDrawableState(extraSpace);
    }

    public void setMessageReaded(boolean state){
        if (mMessageReaded != state){
            this.mMessageReaded = state;
            refreshDrawableState();
        }
    }
}
