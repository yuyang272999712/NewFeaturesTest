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
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 刮刮卡效果的View
 */
public class GuaguakaTextView extends View {
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
     * 画笔宽度(可以通过设置自定义属性获取)
     */
    private int paintWidth = 80;
    /**
     * 遮盖层颜色
     */
    private int color = Color.parseColor("#c0c0c0");

    /**
     * ------------------------以下是奖区的一些变量
     */
    //判断是否已经刮开
    private boolean isComplete;

    private Paint mBackPint = new Paint();
    private Rect mTextBound = new Rect();
    private String mText = "￥500,0000";

    private int lastX;
    private int lastY;

    public GuaguakaTextView(Context context) {
        this(context, null);
    }

    public GuaguakaTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuaguakaTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化画笔
        setUpFingerPaint();
        //初始化刮奖区画笔
        setUpBackPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        //初始化bitmap
        mCacheBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCacheCanvas = new Canvas(mCacheBitmap);

        //绘制遮盖层（即刮刮卡的锡层）
        fingerPaint.setStyle(Paint.Style.FILL);
        mCacheCanvas.drawRoundRect(new RectF(0, 0, width, height), 30, 30,
                fingerPaint);
        mCacheCanvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
                R.mipmap.guaguaka_foreground), null, new RectF(0, 0, width, height), null);
    }

    /**
     * 初始化canvas的绘制用的画笔
     */
    private void setUpBackPaint() {
        mBackPint.setStyle(Paint.Style.FILL);
        mBackPint.setTextScaleX(2f);
        mBackPint.setColor(Color.DKGRAY);
        mBackPint.setTextSize(32);
        mBackPint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    /**
     * 设置画笔
     */
    private void setUpFingerPaint(){
        fingerPaint.setColor(color);
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
        //先画刮开要显示的文字
        canvas.drawText(mText, getWidth() / 2 - mTextBound.width() / 2,
                getHeight() / 2 + mTextBound.height() / 2, mBackPint);
        if (!isComplete) {
            //在缓存mBitmap上绘制图像
            drawPath();
            //将缓存mBitmap上的图像绘制到屏幕上
            canvas.drawBitmap(mCacheBitmap, 0, 0, null);
        }
    }

    /**
     * 绘制线条
     */
    private void drawPath() {
        fingerPaint.setStyle(Paint.Style.STROKE);
        fingerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mCacheCanvas.drawPath(fingerPath, fingerPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isComplete){//如果已经完成刮卡
            return false;
        }
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
            case MotionEvent.ACTION_UP:
                //TODO yuyang 检查刮开区域的百分比
                new Thread(mRunnable).start();
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 统计擦除区域任务
     * 我们在ACTION_UP的时候就行计算，首先我们还是给大家灌输下计算的原理，如果大家用心看了，
     * 应该知道我们所有的操作基本都在mBitmap，现在我们获得mBItmap上所有的像素点的数据，统计被清除的区域（被清除的像素为0）；
     * 最后与我们图片的总像素数做个除法元算，就可以拿到我们清除的百分比了；
     * 不过，计算可能会是一个耗时的操作，具体速度跟图片大小有关，所以我们决定使用异步的方式去计算：
     */
    private Runnable mRunnable = new Runnable() {
        private int[] mPixels;

        @Override
        public void run() {
            Log.e("--yuyang--", "统计擦除区域任务");
            int w = getWidth();
            int h = getHeight();

            float wipeArea = 0;
            float totalArea = w * h;

            Bitmap bitmap = mCacheBitmap;

            mPixels = new int[w * h];

            /**
             * 拿到所有的像素信息
             */
            bitmap.getPixels(mPixels, 0, w, 0, 0, w, h);

            /**
             * 遍历统计擦除的区域
             */
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    int index = i + j * w;
                    if (mPixels[index] == 0) {
                        wipeArea++;
                    }
                }
            }

            /**
             * 根据所占百分比，进行一些操作
             */
            if (wipeArea > 0 && totalArea > 0) {
                int percent = (int) (wipeArea * 100 / totalArea);
                if (percent > 70) {
                    Log.e("--yuyang--", "清除区域达到70%，下面自动清除");
                    isComplete = true;
                    postInvalidate();
                }
            }
        }
    };
}
