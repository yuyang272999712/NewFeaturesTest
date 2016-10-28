package com.yuyang.fitsystemwindowstestdrawer.viewPagerIndicator.changeColorIcon;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 仿微信底部导航tab样式
 */
public class ChangeColorIconWithTextView extends View {
    /**
     * 用于绘制缓存区的相关变量
     */
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mPaint;
    /**
     * 颜色
     */
    private int color = 0xFF45C01A;
    /**
     * 透明度
     */
    private float mAlpha = 0f;
    /**
     * icon图标
     */
    private Bitmap mIconBitmap;
    /**
     * 图标显示区域
     */
    private Rect mIconRect;
    /**
     * icon底部文字相关变量
     */
    private String mText = "TAB";
    private int mTextSize = 10;
    private Rect mTextBound = new Rect();
    private Paint mTextPaint;

    public ChangeColorIconWithTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ChangeColorIconWithTextView);
        mIconBitmap = ((BitmapDrawable)array.getDrawable(R.styleable.ChangeColorIconWithTextView_bottom_icon)).getBitmap();
        color = array.getColor(R.styleable.ChangeColorIconWithTextView_bottom_color, color);
        mText = array.getString(R.styleable.ChangeColorIconWithTextView_text);
        mTextSize = array.getDimensionPixelSize(R.styleable.ChangeColorIconWithTextView_text_size,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mTextSize, getResources().getDisplayMetrics()));
        array.recycle();

        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0xff555555);
        //获取text的绘制范围
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //得到Icon的宽度
        int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - mTextBound.height());
        int left = getMeasuredWidth()/2 - iconWidth/2;
        int top = (getMeasuredHeight() - mTextBound.height())/2 - iconWidth/2;
        mIconRect = new Rect(left, top, left+iconWidth, top+iconWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * 绘制步骤
             1、计算alpha（默认为0）
             2、绘制原图
             3、在绘图区域，绘制一个纯色块（设置了alpha），此步绘制在内存的bitmap上
             4、设置mode，针对内存中的bitmap上的paint
             5、绘制我们的图标，此步绘制在内存的bitmap上
             6、绘制原文本
             7、绘制设置alpha和颜色后的文本
             8、将内存中的bitmap绘制出来
         */
        int alpha = (int) Math.ceil(255 * mAlpha);
        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);
        //3~5步
        setupTargetBitmap(alpha);
        drawSourceText(canvas, alpha);
        drawTargetText(canvas, alpha);
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    private void setupTargetBitmap(int alpha) {
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);
        mCanvas.drawRect(mIconRect, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
    }

    private void drawSourceText(Canvas canvas, int alpha) {
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0xff333333);
        mTextPaint.setAlpha(255 - alpha);
        canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2 - mTextBound.width() / 2,
                mIconRect.bottom + mTextBound.height(), mTextPaint);
    }

    private void drawTargetText(Canvas canvas, int alpha) {
        mTextPaint.setColor(color);
        mTextPaint.setAlpha(alpha);
        canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2 - mTextBound.width() / 2,
                mIconRect.bottom + mTextBound.height(), mTextPaint);
    }

    /**
     * 对外公布的设置透明度的方法
     * @param alpha
     */
    public void setIconAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidateView();
    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }
}
