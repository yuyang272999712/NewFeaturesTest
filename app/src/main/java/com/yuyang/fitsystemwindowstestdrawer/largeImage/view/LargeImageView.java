package com.yuyang.fitsystemwindowstestdrawer.largeImage.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.OverScroller;

import java.io.IOException;
import java.io.InputStream;

/**
 * BitmapRegionDecoder加载超大图
 */
public class LargeImageView extends View {
    private BitmapRegionDecoder mDecoder;
    //图片宽高
    private int mImageWidth, mImageHeight;
    //绘制区域
    private Rect mRect = new Rect();
    //手势监听
    private MoveGestureDetector mDetector;
    //滑动速度
    private VelocityTracker velocityTracker;
    //加速滚动
    private OverScroller overScroller;

    private static final BitmapFactory.Options options = new BitmapFactory.Options();

    static
    {
        options.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    public LargeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        velocityTracker = VelocityTracker.obtain();
        overScroller = new OverScroller(context, new AccelerateDecelerateInterpolator());
        init();
    }

    private void init() {
        mDetector = new MoveGestureDetector(getContext(), new MoveGestureDetector.SimpleMoveGestureDetector(){
            @Override
            public boolean onMove(MoveGestureDetector detector) {
                int moveX = (int) detector.getMoveX();
                int moveY = (int) detector.getMoveY();

                if(mImageWidth > getWidth()){
                    mRect.offset(-moveX, 0);
                    checkWidth();
                }
                if (mImageHeight > getHeight()) {
                    mRect.offset(0, -moveY);
                    checkHeight();
                }
                invalidate();

                return true;
            }
        });
    }

    private void checkWidth() {
        Rect rect = mRect;
        int imageWidth = mImageWidth;

        if(rect.right > imageWidth){
            rect.right = imageWidth;
            rect.left = imageWidth - getWidth();
        }
        if(rect.left < 0){
            rect.left = 0;
            rect.right = getWidth();
        }
    }

    private void checkHeight() {
        Rect rect = mRect;
        int imageHeight = mImageHeight;

        if(rect.bottom > imageHeight){
            rect.bottom = imageHeight;
            rect.top = imageHeight - getHeight();
        }
        if(rect.top < 0){
            rect.top = 0;
            rect.bottom = getHeight();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();

        int imageWidth = mImageWidth;
        int imageHeight = mImageHeight;

        mRect.left = imageWidth/2 - viewWidth/2;
        mRect.top = imageHeight/2 - viewHeight/2;
        mRect.right = mRect.left + viewWidth;
        mRect.bottom = mRect.top + viewHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = mDecoder.decodeRegion(mRect, options);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        velocityTracker.addMovement(event);
        mDetector.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP){
            velocityTracker.computeCurrentVelocity(1000, 1000);
            int xVelocity = (int) velocityTracker.getXVelocity();
            int yVelocity = (int) velocityTracker.getYVelocity();
            overScroller.fling(0,0,xVelocity,yVelocity,-mImageWidth,mImageWidth,-mImageHeight,mImageHeight);
            invalidate();
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (overScroller.computeScrollOffset()){
            if(mImageWidth > getWidth()){
                mRect.offset(-overScroller.getCurrX(), 0);
                checkWidth();
            }
            if (mImageHeight > getHeight()) {
                mRect.offset(0, -overScroller.getCurrY());
                checkHeight();
            }
            Log.e("LargeImageView", "X轴滚动距离："+overScroller.getCurrX());
            invalidate();
        }
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setInputStream(InputStream inputStream){
        try {
            mDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);
            mImageWidth = options.outWidth;
            mImageHeight = options.outHeight;

            requestLayout();
            invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
