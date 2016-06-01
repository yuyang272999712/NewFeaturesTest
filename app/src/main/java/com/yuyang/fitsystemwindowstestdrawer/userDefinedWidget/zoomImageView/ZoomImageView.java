package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.zoomImageView;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * 可缩放的ImageView，具体可使用github上的PhotoView项目
 * 主要利用了Matrix来进行缩放和移动
 */
public class ZoomImageView extends ImageView implements View.OnTouchListener,
        ScaleGestureDetector.OnScaleGestureListener, ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = ZoomImageView.class.getName();
    /**
     *  最大放大4倍
     */
    private static final float SCALE_MAX = 4.0f;
    private static final float SCALE_MID = 2.0f;
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
     * 缩放的手势检测
     */
    private ScaleGestureDetector mScaleGestureDetector = null;
    /**
     * 用于双击检测
     */
    private GestureDetector mGestureDetector;
    private boolean isAutoScale;//是否正在自动缩放

    /**
     * 拖动相关
     */
    private int mTouchSlop;//判断是拖动的最小距离
    private float mLastX;//上一次触摸的x坐标
    private float mLastY;//上一次触摸的y坐标
    private boolean isCanDrag;//是否可拖动
    private int lastPointerCount;//上一次的触碰点数
    private boolean isCheckTopAndBottom = true;
    private boolean isCheckLeftAndRight = true;

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
        //获取系统拖动最小判断距离
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        //初始化手势缩放
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        //初始化手势监听
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (isAutoScale){
                    return true;
                }
                float x = e.getX();
                float y = e.getY();
                if(getScale() < SCALE_MID){
                    isAutoScale = true;
                    ZoomImageView.this.postDelayed(new AutoScaleRunnable(SCALE_MID, x, y), 10);
                }else if (getScale() >= SCALE_MID && getScale() < SCALE_MAX){
                    isAutoScale = true;
                    ZoomImageView.this.postDelayed(new AutoScaleRunnable(SCALE_MAX, x, y), 10);
                }else {
                    isAutoScale = true;
                    ZoomImageView.this.postDelayed(new AutoScaleRunnable(initScale, x, y), 10);
                }
                return true;
            }
        });
        this.setOnTouchListener(this);
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
        if (mGestureDetector.onTouchEvent(event)){
            return true;
        }
        mScaleGestureDetector.onTouchEvent(event);

        float x=0,y=0;
        //触摸点个数
        int pointerCount = event.getPointerCount();
        for (int i=0; i<pointerCount; i++){
            x += event.getX(i);
            y += event.getY(i);
        }
        x = x/pointerCount;
        y = y/pointerCount;

        //如果触碰点数发生变化，重置mLasX , mLastY
        if (pointerCount != lastPointerCount){
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }
        lastPointerCount = pointerCount;

        RectF rectF = getMatrixRectF();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (rectF.width() > getWidth() || rectF.height() > getHeight()) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (rectF.width() > getWidth() || rectF.height() > getHeight()) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                float dx = x - mLastX;
                float dy = y - mLastY;//每次移动的距离
                if (!isCanDrag){
                    isCanDrag = isCanDrag(dx, dy);
                }
                if (isCanDrag){
                    /**
                     * 解决ViewPager滑动冲突
                     */
                    if (getMatrixRectF().left == 0 && dx > 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    if (getMatrixRectF().right == getWidth() && dx < 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    isCheckLeftAndRight = isCheckTopAndBottom = true;
                    // 如果宽度小于屏幕宽度，则禁止左右移动
                    if (rectF.width() < getWidth()){
                        dx = 0;
                        isCheckLeftAndRight = false;
                    }
                    // 如果高度小雨屏幕高度，则禁止上下移动
                    if (rectF.height() < getHeight()){
                        dy = 0;
                        isCheckTopAndBottom = false;
                    }
                    mScaleMatrix.postTranslate(dx, dy);
                    checkMatrixBounds();
                    setImageMatrix(mScaleMatrix);
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                lastPointerCount = 0;
                break;
        }

        return true;
    }

    /**
     * 不能把图片移动的与屏幕边界出现白边，校验完成后，调用setImageMatrix.
     *  移动时，进行边界判断，主要判断宽或高大于屏幕的
     */
    private void checkMatrixBounds() {
        RectF rect = getMatrixRectF();

        float deltaX = 0, deltaY = 0;
        final float viewWidth = getWidth();
        final float viewHeight = getHeight();
        // 判断移动或缩放后，图片显示是否超出屏幕边界
        if (rect.top > 0 && isCheckTopAndBottom)
        {
            deltaY = -rect.top;
        }
        if (rect.bottom < viewHeight && isCheckTopAndBottom)
        {
            deltaY = viewHeight - rect.bottom;
        }
        if (rect.left > 0 && isCheckLeftAndRight)
        {
            deltaX = -rect.left;
        }
        if (rect.right < viewWidth && isCheckLeftAndRight)
        {
            deltaX = viewWidth - rect.right;
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 判断是否是拖动
     * @param dx
     * @param dy
     * @return
     */
    private boolean isCanDrag(float dx, float dy) {
        return Math.sqrt((dx*dx) + (dy*dy)) >= mTouchSlop;
    }

    private float getScale(){
        mScaleMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    /**
     * 自动完成缩放任务动画
     */
    private class AutoScaleRunnable implements Runnable {
        static final float BIGGER = 1.07f;
        static final float SMALLER = 0.93f;
        private float mTargetScale;
        private float tmpScale;
        /**
         * 缩放的中心
         */
        private float x;
        private float y;

        /**
         * 传入目标缩放值，根据目标值与当前值，判断应该放大还是缩小
         * @param targetScale
         * @param x
         * @param y
         */
        public AutoScaleRunnable(float targetScale, float x, float y) {
            this.mTargetScale = targetScale;
            this.x = x;
            this.y = y;
            if (getScale() < mTargetScale) {
                tmpScale = BIGGER;
            } else {
                tmpScale = SMALLER;
            }

        }

        @Override
        public void run() {
            // 进行缩放
            mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);

            final float currentScale = getScale();
            //如果值在合法范围内，继续缩放
            if (((tmpScale > 1f) && (currentScale < mTargetScale))
                    || ((tmpScale < 1f) && (mTargetScale < currentScale))) {
                ZoomImageView.this.postDelayed(this, 10);
            } else{//设置为目标的缩放比例
                final float deltaScale = mTargetScale / currentScale;
                mScaleMatrix.postScale(deltaScale, deltaScale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);
                isAutoScale = false;
            }
        }
    }
}
