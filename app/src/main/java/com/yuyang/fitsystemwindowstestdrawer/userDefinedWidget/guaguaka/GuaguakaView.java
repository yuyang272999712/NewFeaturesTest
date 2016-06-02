package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.guaguaka;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 刮刮卡效果的View
 * 主要还是应用Paint.setXfermode方法
 */
public class GuaguakaView extends View {
    /**
     * 手指移动的画笔
     */
    private Paint fingerPaint = new Paint();
    /**
     * 手指移动的路线
     */
    private Path fingerPath = new Path();
    /**
     * 在内存中搞一个mCanvas，创建了一个mBitmap，
     * 然后通过mCanvas使用我们预先设置的mOuterPaint在我们的mBitmap上绘制mPath；
     */
    private Canvas mCacheCanvas;
    /**
     * 绘制在mCanvas上的bitmap
     */
    private Bitmap mCacheBitmap;
    /**
     * 刮卡要显示的图片
     */
    private Bitmap mBackBitmap;
    /**
     * 画笔宽度(可以通过设置自定义属性获取)
     */
    private int paintWidth = 80;
    /**
     * 遮盖层颜色
     */
    private int color = Color.parseColor("#c0c0c0");

    private int lastX;
    private int lastY;

    public GuaguakaView(Context context) {
        this(context, null);
    }

    public GuaguakaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuaguakaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBackBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.zoom_img_2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //初始化bitmap
        mCacheBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCacheCanvas = new Canvas(mCacheBitmap);
        //初始化画笔
        setUpOutPaint();
        //绘制遮盖层（即刮刮卡的锡层）
        mCacheCanvas.drawColor(color);
    }

    /**
     * 设置画笔
     */
    private void setUpOutPaint(){
        fingerPaint.setColor(Color.RED);
        fingerPaint.setAntiAlias(true);
        fingerPaint.setDither(true);
        fingerPaint.setStyle(Paint.Style.STROKE);
        //设置两端都是圆角
        fingerPaint.setStrokeJoin(Paint.Join.ROUND);
        fingerPaint.setStrokeCap(Paint.Cap.ROUND);
        //设置画笔宽度
        fingerPaint.setStrokeWidth(paintWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //先画刮开要显示的图片
        canvas.drawBitmap(mBackBitmap, 0, 0, null);
        //在缓存mBitmap上绘制图像
        drawPath();
        //将缓存mBitmap上的图像绘制到屏幕上
        canvas.drawBitmap(mCacheBitmap, 0, 0, null);
    }

    /**
     * 绘制线条
     */
    private void drawPath() {
        //TODO yuyang 关键代码
        fingerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mCacheCanvas.drawPath(fingerPath, fingerPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                fingerPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = Math.abs(x - lastX);
                int dy = Math.abs(y - lastY);
                if (dx>3 || dy>3){
                    fingerPath.lineTo(x, y);
                }
                lastX = x;
                lastY = y;
                break;
        }
        invalidate();
        return true;
    }
}
