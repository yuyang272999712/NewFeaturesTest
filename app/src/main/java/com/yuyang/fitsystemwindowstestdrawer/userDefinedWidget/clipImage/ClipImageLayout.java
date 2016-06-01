package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.clipImage;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * 裁剪图片
 */
public class ClipImageLayout extends RelativeLayout {
    private ClipImageBorderView borderView;
    private ZoomImageView zoomImageView;

    public ClipImageLayout(Context context) {
        this(context, null);
    }

    public ClipImageLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipImageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        borderView = new ClipImageBorderView(context, attrs);
        zoomImageView = new ZoomImageView(context, attrs);

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(zoomImageView, lp);
        this.addView(borderView, lp);
    }

    public Bitmap clip(){
        return zoomImageView.clip();
    }
}
