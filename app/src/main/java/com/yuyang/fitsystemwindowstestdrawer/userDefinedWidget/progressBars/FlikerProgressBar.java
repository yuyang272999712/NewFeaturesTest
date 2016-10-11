package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.progressBars;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.DensityUtils;

/**
 * 带闪烁的进度条
 */
public class FlikerProgressBar extends ProgressBar implements Runnable {
    private PorterDuffXfermode mXfermodeText;//文本绘制样式
    private PorterDuffXfermode mXfermodeFliker;//闪烁绘制样式
    private Context mContext;
    private Paint mTextPaint;
    private Paint mPaint;
    private Bitmap flickerBitmap;//闪烁的图片
    private int flickerLeft;//闪烁滑块的左边位置
    private int loadingColor = Color.parseColor("#40c4ff");//文字、下载中进度条颜色
    private String loadingText = "下载中 %d";
    private Rect textRect;//文字绘制范围
    private int width;
    private int height;
    private boolean attached = false;//是否显示在屏幕上
    private Canvas mCanvas;
    private Bitmap progressBitmap;//先将进度条绘制在这上

    public FlikerProgressBar(Context context) {
        this(context, null);
    }

    public FlikerProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlikerProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(loadingColor);
        mTextPaint.setTextSize(DensityUtils.sp2px(mContext, 12));
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(loadingColor);
        mXfermodeText = new PorterDuffXfermode(PorterDuff.Mode.XOR);
        mXfermodeFliker = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
        textRect = new Rect();
        flickerBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.flicker);
        flickerLeft = -flickerBitmap.getWidth();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        attached = true;
        new Thread(this).start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        attached = false;
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        progressBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(progressBitmap);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画边框
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(DensityUtils.dp2px(mContext, 1));
        canvas.drawRect(0, 0, width, height, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);
        float radio = getProgress()*1.0f/getMax();//当前进度和总值的比例
        float progressPosX = width*radio;//已达到的宽度

        //画带滑块的进度条
        //画进度
        mCanvas.save(Canvas.CLIP_SAVE_FLAG);
        mCanvas.clipRect(0, 0, progressPosX, height);
        mCanvas.drawColor(loadingColor);
        //画闪烁的图片
        mPaint.setXfermode(mXfermodeFliker);
        mCanvas.drawBitmap(flickerBitmap, flickerLeft, 0, mPaint);
        mPaint.setXfermode(null);
        mCanvas.restore();

        int saveLayer = canvas.saveLayer(0, 0, width, height, mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(progressBitmap, 0, 0, mPaint);
        //画文字
        String text = String.format(loadingText, getProgress())+"%";
        mTextPaint.getTextBounds(text, 0, text.length(), textRect);
        int tWidth = textRect.width();
        int tHeight = textRect.height();
        float xCoordinate = (getMeasuredWidth() - tWidth) / 2;
        float yCoordinate = (getMeasuredHeight() + tHeight) / 2;
        mTextPaint.setXfermode(mXfermodeText);
        canvas.drawText(text, xCoordinate, yCoordinate, mTextPaint);
        mTextPaint.setXfermode(null);
        canvas.restoreToCount(saveLayer);
    }

    @Override
    public void run() {
        while (attached){
            flickerLeft += DensityUtils.dp2px(mContext, 5);
            float progressWidth = (getProgress()*1.0f / getMax()) * width;
            if(flickerLeft >= progressWidth){
                flickerLeft = -flickerBitmap.getWidth();
            }
            postInvalidate();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
