package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.blurredEffect.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.blurredEffect.util.BitmapUtil;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.blurredEffect.util.BlurBitmap;

/**
 * ！！！有自定义属性
 * 实现渐变模糊
 */
public class BlurredView extends RelativeLayout {
    /**
     * 图片显示模式
     */
    private ScaleType scaleType;
    private static final ScaleType[] sScaleTypeArray = {
            ScaleType.MATRIX,
            ScaleType.FIT_XY,
            ScaleType.FIT_START,
            ScaleType.FIT_CENTER,
            ScaleType.FIT_END,
            ScaleType.CENTER,
            ScaleType.CENTER_CROP,
            ScaleType.CENTER_INSIDE
    };
    /**
     * 上层清晰原图
     */
    private ImageView mOriginImg;
    /**
     * 下层最大模糊图像
     */
    private ImageView mBlurredImg;
    /**
     * 原图Bitmap
     */
    private Bitmap mOriginBitmap;
    /**
     * 模糊后的Bitmap
     */
    private Bitmap mBlurredBitmap;
    /**
     * Context
     */
    private Context mContext;

    public BlurredView(Context context) {
        this(context, null);
    }

    public BlurredView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlurredView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.custom_layout_blur_view, this);
        mBlurredImg = (ImageView) findViewById(R.id.blur_view_blurred_img);
        mOriginImg = (ImageView) findViewById(R.id.blur_view_origin_img);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BlurredView);
        Drawable drawable = array.getDrawable(R.styleable.BlurredView_android_src);
        int index = array.getInt(R.styleable.BlurredView_android_scaleType, 6);
        setScaleType(sScaleTypeArray[index]);
        array.recycle();

        //生成模糊图像
        if (null != drawable) {
            mOriginBitmap = BitmapUtil.drawableToBitmap(drawable);
            mBlurredBitmap = BlurBitmap.blur(context, mOriginBitmap);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBlurredImg.setScaleType(scaleType);
        mOriginImg.setScaleType(scaleType);
        mBlurredImg.setImageBitmap(mBlurredBitmap);
        mOriginImg.setImageBitmap(mOriginBitmap);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mOriginBitmap.recycle();
        mBlurredBitmap.recycle();
    }

    /**
     * 重置图片的显示模式
     * @param scaleType
     */
    public void setScaleType(ImageView.ScaleType scaleType) {
        if (scaleType == null) {
            throw new NullPointerException();
        }

        if (this.scaleType != scaleType) {
            this.scaleType = scaleType;
            requestLayout();
            invalidate();
        }
    }

    /**
     * 设置模糊程度
     * @param level 模糊程度, 数值在 [0,1] 之间.
     */
    public void setBlurredLevel(float level) {
        if (level < 0 || level > 1) {
            throw new IllegalStateException("模糊之必须在[0,1]区间");
        }
        mOriginImg.setAlpha(1-level);
    }
}
