package com.yuyang.fitsystemwindowstestdrawer.largeImage.view;

import android.content.Context;
import android.view.MotionEvent;

/**
 * Created by yuyang on 16/3/8.
 */
public abstract class BaseGestureDetector {
    protected boolean mGestureInProgress;

    protected MotionEvent mPreMotionEvent;
    protected MotionEvent mCurrentMotionEvent;

    protected Context mContext;

    public BaseGestureDetector(Context context){
        this.mContext = context;
    }

    public boolean onTouchEvent(MotionEvent event){
        if(!mGestureInProgress){
            handleStartProgressEvent(event);
        }else {
            handleInProgressEvent(event);
        }

        return true;
    }

    protected abstract void handleInProgressEvent(MotionEvent event);

    protected abstract void handleStartProgressEvent(MotionEvent event);

    protected abstract void updateStateByEvent(MotionEvent event);

    protected void restState(){
        if(mPreMotionEvent != null){
            mPreMotionEvent.recycle();
            mPreMotionEvent = null;
        }
        if(mCurrentMotionEvent != null){
            mCurrentMotionEvent.recycle();
            mCurrentMotionEvent = null;
        }
        mGestureInProgress = false;
    }
}
