package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.fragmentAbout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by yuyang on 2017/2/8.
 */

@SuppressLint("AppCompatCustomView")
public class MyEditTextView extends EditText {
    private static final String TAG = "MyEditTextView";

    public MyEditTextView(Context context) {
        super(context);
    }

    public MyEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Log.e(TAG, "onSaveInstanceState");
        return super.onSaveInstanceState();
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        Log.e(TAG, "onRestoreInstanceState");
        super.onRestoreInstanceState(state);
    }
}
