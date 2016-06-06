package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.zhuanpan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * SurfaceView实现转盘抽奖效果
 */
public class LuckyPanView extends SurfaceView implements Callback,Runnable {
    private SurfaceHolder mHolder;
    /**
     * 与SurfaceHolder绑定的canvas
     */
    private Canvas mCanvas;
    /**
     * 用于绘制的线程
     */
    private Thread t;
    /**
     * 线程的控制开关
     */
    private boolean isRunning;
    /**
     * 抽奖的文字
     */
    private String[] mStrs = new String[] { "单反相机", "IPAD", "恭喜发财", "IPHONE",
            "妹子一只", "恭喜发财", "恭喜发财", "恭喜发财" };
    /**
     * 每个盘块的颜色
     */
    private int[] mColors = new int[] { 0xFFFFC300, 0xFFF17E01, 0xFFFFC300,
            0xFFF17E01, 0xFFFFC300, 0xFFF17E01, 0xFFFFC300, 0xFFF17E01 };
    /**
     * 与文字对应的图片
     */
    private int[] mImgs = new int[] {R.mipmap.zhuanpan_danfan, R.mipmap.zhuanpan_ipad,
            R.mipmap.zhuanpan_xiexie, R.mipmap.zhuanpan_iphone, R.mipmap.zhuanpan_meizi,
            R.mipmap.zhuanpan_xiexie,R.mipmap.zhuanpan_xiexie,R.mipmap.zhuanpan_xiexie };
    /**
     * 与图片对应的bitmap
     */
    private Bitmap[] mImgsBitmap;
    /**
     * TODO yuyang 这个数字应该根据mStrs.length获取
     * 盘块个数
     */
    private int mItemCount = 8;
    /**
     * 绘制盘块的范围
     */
    private RectF mRange = new RectF();
    /**
     * TODO 圆的！直径！
     */
    private int mRadius;
    /**
     * 绘制盘块的画笔
     */
    private Paint mArcPaint;
    /**
     * 绘制文字的画笔
     */
    private Paint mTextPaint;
    /**
     * 滚动的速度
     */
    private double mSpeed;
    /**
     * 滚动开始时的角度
     */
    private volatile float mStartAngle = 0;
    /**
     * 是否点击了停止
     */
    private boolean isShouldEnd;
    /**
     * 控价的中心位置
     */
    private int mCenter;
    /**
     * 控件的padding，以paddingLeft为标准,4个padding的值一致，
     */
    private int mPadding;
    /**
     * 空间背景
     */
    private Bitmap mBgBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.zhuanpan_bg2);
    /**
     * 文字的大小
     */
    private float mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics());

    public LuckyPanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);

        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = Math.min(getMeasuredHeight(), getMeasuredWidth());
        mPadding = getPaddingLeft();
        setPadding(mPadding,mPadding,mPadding,mPadding);
        //获取圆的直径
        mRadius = width - getPaddingLeft() - getPaddingRight();
        //中心点
        mCenter = width/2;
        //设置控件为正方形
        setMeasuredDimension(width,width);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //初始化圆弧画笔
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setDither(true);
        //初始化文字画笔
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(Color.parseColor("#ffffffff"));
        //圆弧绘制范围 TODO mRadius为直径！！
        mRange = new RectF(getPaddingLeft(),getPaddingTop(),mRadius+getPaddingLeft(),mRadius+getPaddingTop());
        //初始化图片
        mImgsBitmap = new Bitmap[mItemCount];
        for (int i=0;i<mItemCount;i++){
            mImgsBitmap[i] = BitmapFactory.decodeResource(getResources(), mImgs[i]);
        }
        //TODO 开启线程
        isRunning = true;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // 通知关闭线程
        isRunning = false;
    }

    @Override
    public void run() {
        //不断地进行draw
        while (isRunning){
            long start = System.currentTimeMillis();
            draw();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 50) {
                    Thread.sleep(50 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void draw() {
        try {
            //获取canvas
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null){
                //绘制背景
                drawBg();
            }
            /**
             * 绘制圆块，每个块上的文字与图片
             */
            float tmpAngle = mStartAngle;
            float sweepAngle = 360*1.0f/mItemCount;
            for (int i=0; i<mItemCount; i++){
                //绘制圆块
                mArcPaint.setColor(mColors[i]);
                mCanvas.drawArc(mRange,tmpAngle,sweepAngle,true,mArcPaint);
                //绘制文本
                drawText(tmpAngle, sweepAngle, mStrs[i]);
                //绘制图片
                drawIcon(tmpAngle, i);
                //角度累加
                tmpAngle += sweepAngle;
            }
            //如果mSpeed不为0，相当于在滚动
            mStartAngle += mSpeed;
            // 点击停止时，设置mSpeed为递减，为0值转盘停止
            if (isShouldEnd) {
                mSpeed -= 1;
            }
            if (mSpeed <= 0) {
                mSpeed = 0;
                isShouldEnd = false;
            }
            // 根据当前旋转的mStartAngle计算当前滚动到的区域
            calInExactArea(mStartAngle);
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null)
                mHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    /**
     * 根据当前旋转的startAngle计算当前滚动到的区域
     * TODO 然而并没用什么卵用，主要是为了打印log看看 oO(^_^)Oo
     * @param startAngle
     */
    private void calInExactArea(float startAngle) {
        // 让指针从水平向右开始计算
        float rotate = startAngle + 90;
        rotate %= 360.0;
        for (int i = 0; i < mItemCount; i++) {
            // 每个的中奖范围
            float from = 360 - (i + 1) * (360 / mItemCount);
            float to = from + 360 - (i) * (360 / mItemCount);

            if ((rotate > from) && (rotate < to))
            {
                Log.d("TAG", mStrs[i]);
                return;
            }
        }
    }

    /**
     * 绘制Icon
     * @param startAngle
     * @param i
     */
    private void drawIcon(float startAngle, int i) {
        //TODO yuyang 设置图片的宽度为直径的1/8
        int imgWidth = mRadius / 8;
        float disAngel = 360*1.0f / mItemCount / 2;
        float angle = (float) ((disAngel + startAngle) * (Math.PI / 180));
        int x = (int) (mCenter + mRadius / 2 / 2 * Math.cos(angle));
        int y = (int) (mCenter + mRadius / 2 / 2 * Math.sin(angle));
        // 确定绘制图片的位置
        Rect rect = new Rect(x - imgWidth / 2, y - imgWidth / 2, x + imgWidth / 2, y + imgWidth / 2);
        mCanvas.drawBitmap(mImgsBitmap[i], null, rect, null);
    }

    /**
     * 绘制文本
     * @param tmpAngle
     * @param sweepAngle
     * @param mStr
     */
    private void drawText(float tmpAngle, float sweepAngle, String mStr) {
        Path path = new Path();
        path.addArc(mRange, tmpAngle, sweepAngle);
        float textWidth = mTextPaint.measureText(mStr);
        // 利用水平偏移让文字居中 mRadius * Math.PI 是圆的周长；周长/ mItemCount / 2 是每个Arc的一半的长度；
        //拿Arc一半的长度减去textWidth / 2，就把文字设置居中了。
        float hOffset = (float) (mRadius*Math.PI / mItemCount / 2 - textWidth / 2);//水平偏移
        float vOffset = mRadius / 2 / 6;//垂直偏移半径的六分之一
        mCanvas.drawTextOnPath(mStr, path, hOffset, vOffset, mTextPaint);
    }

    /**
     * 绘制背景
     */
    private void drawBg() {
        mCanvas.drawColor(0xFFFFFFFF);
        mCanvas.drawBitmap(mBgBitmap, null, new Rect(mPadding / 2,
                mPadding / 2, getMeasuredWidth() - mPadding / 2,
                getMeasuredWidth() - mPadding / 2), null);
    }

    /**
     * 点击开始旋转
     * @param luckyIndex 要停止在第几个奖项
     */
    public void luckyStart(int luckyIndex) {
        // 每项角度大小
        float angle = (float) (360 / mItemCount);
        // 中奖角度范围（因为指针向上，所以水平第一项旋转到指针指向，需要旋转210-270；）
        float from = 270 - (luckyIndex + 1) * angle;
        float to = from + angle;
        // 停下来时旋转的距离
        float targetFrom = 4 * 360 + from;
        /**
         * <pre>
         *  (v1 + 0) * (v1+1) / 2 = target ;
         *  v1*v1 + v1 - 2target = 0 ;
         *  v1=-1+(1*1 + 8 *1 * target)/2;
         * </pre>
         */
        float v1 = (float) (Math.sqrt(1 * 1 + 8 * 1 * targetFrom) - 1) / 2;
        float targetTo = 4 * 360 + to;
        float v2 = (float) (Math.sqrt(1 * 1 + 8 * 1 * targetTo) - 1) / 2;

        mSpeed = (float) (v1 + Math.random() * (v2 - v1));
        isShouldEnd = false;
    }

    public void luckyEnd() {
        mStartAngle = 0;
        isShouldEnd = true;
    }

    public boolean isStart() {
        return mSpeed != 0;
    }

    public boolean isShouldEnd() {
        return isShouldEnd;
    }
}
