package com.yuyang.fitsystemwindowstestdrawer.tantan.cardFlingView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

/**
 * Created by yuyang on 16/3/11.
 */
public abstract class BaseFlingAdapterView extends AdapterView<BaseAdapter> {

    private int widthMeasureSpec;
    private int heightMeasureSpec;

    public BaseFlingAdapterView(Context context) {
        super(context);
    }

    public BaseFlingAdapterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseFlingAdapterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setSelection(int position) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.widthMeasureSpec = widthMeasureSpec;
        this.heightMeasureSpec = heightMeasureSpec;
    }

    public int getWidthMeasureSpec() {
        return widthMeasureSpec;
    }

    public int getHeightMeasureSpec() {
        return heightMeasureSpec;
    }
}
