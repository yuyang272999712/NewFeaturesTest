package com.yuyang.fitsystemwindowstestdrawer.userDefinedViews.zoomImageView;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by yuyang on 16/5/23.
 */
public class ZoomImageView extends ImageView implements View.OnTouchListener,
        ScaleGestureDetector.OnScaleGestureListener, ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = ZoomImageView.class.getName();
    /**
     *  最大放大4倍
     */
    private static final float SCALE_MAX = 4.0f;
    /**
     * 初始化时的缩放比例，如果图片大于显示区域，initScale值小于1
     */
    private float initScale = 1.0f;
    /**
     * 用于缩放的矩阵
     */
    private final Matrix mScaleMatrix = new Matrix();
    /**
     * 存放矩阵的9个值
     */
    private final float[] matrixValues = new float[9];
    /**
     * 是否是第一次加载，如果不是就不用再计算初始缩放比例initScale了
     */
    private boolean once = true;
    /**
     * 手势监听器
     */
    private ScaleGestureDetector mScaleGestureDetector = null;

    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //ImageView专有方法，设置缩放模式
        super.setScaleType(ScaleType.MATRIX);
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), this);
        setOnTouchListener(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }else {
            getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }

    /**
     * OnGlobalLayoutListener实现方法，用于监听控价加载到屏幕上以后的动作
     */
    @Override
    public void onGlobalLayout() {
        if (once) {
            Drawable drawable = getDrawable();
            if (drawable == null){
                return;
            }
            int width = getWidth();
            int height = getHeight();
            //获取图片的真是宽高
            int dw = drawable.getIntrinsicWidth();
            int dh = drawable.getIntrinsicHeight();
            float scale = 1.0f;
            //如果图片的宽或高大于屏幕，则缩放至屏幕的宽或高
            if (dw > width && dh <= height){
                scale = width * 1.0f / dw;
            }
            if (dh > height && dw <= width){
                scale = height * 1.0f / dh;
            }
            //如果图片宽高都大于屏幕
            if(dw > width && dh > height){
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }
            initScale = scale;
            //图片移动至屏幕中心然后进行缩放
            mScaleMatrix.postTranslate((width - dw)/2, (height - dh)/2);
            mScaleMatrix.postScale(scale,scale,width/2,height/2);
            setImageMatrix(mScaleMatrix);

            once = false;
        }
    }

    /**
     * OnScaleGestureListener缩放手势监听－begin
     * @param detector
     * @return
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();

        if (getDrawable() == null)
            return true;

        /**
         * 缩放的范围控制
         */
        if ((scale < SCALE_MAX && scaleFactor > 1.0f)
                || (scale > initScale && scaleFactor < 1.0f)) {
            /**
             * 最大值最小值判断
             */
            if (scaleFactor * scale < initScale) {//如果缩放比例小于最小缩放比例
                scaleFactor = initScale / scale;//这样scale＊scaleFactor＝initScale
            }
            if (scaleFactor * scale > SCALE_MAX) {
                scaleFactor = SCALE_MAX / scale;
            }
            /**
             * 设置缩放比例,与缩放中心
             * TODO yuyang 该方法是在原有缩放基础之上继续缩放
             */
            mScaleMatrix.postScale(scaleFactor, scaleFactor,
                    detector.getFocusX(), detector.getFocusY());
            //移动图片
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
        }
        return true;
    }

    /**
     * 缩放时进行图片显示位置的控制
     */
    private void checkBorderAndCenterWhenScale(){
        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        // 如果宽或高大于屏幕，则控制范围
        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
        }
        // 如果宽或高小于屏幕，则让其居中
        if (rect.width() < width) {
            deltaX = width * 0.5f - rect.right + 0.5f * rect.width();
        }
        if (rect.height() < height) {
            deltaY = height * 0.5f - rect.bottom + 0.5f * rect.height();
        }
        Log.i(TAG, "deltaX = " + deltaX + " , deltaY = " + deltaY);

        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 根据图片的Matrix获取图片的范围
     * @return
     */
    private RectF getMatrixRectF(){
        Matrix matrix = mScaleMatrix;
        RectF rect = new RectF();
        Drawable a = getDrawable();
        if (a != null){
            rect.set(0,0,a.getIntrinsicWidth(),a.getIntrinsicHeight());
            matrix.mapRect(rect);
        }
        return rect;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }
    //OnScaleGestureListener缩放手势监听－end

    /**
     * OnTouchListener实现方法
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mScaleGestureDetector.onTouchEvent(event);
    }

    private float getScale(){
        mScaleMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }
}
