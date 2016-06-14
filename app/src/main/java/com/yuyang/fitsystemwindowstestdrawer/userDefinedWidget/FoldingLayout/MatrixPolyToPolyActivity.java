package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.FoldingLayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 折叠的实现原理：
 *
 * 想要实现折叠，最重要的就是其核心的原理了，如何能把一张正常显示的图片，让它能够进行偏移显示。
 * 其实精髓就在于Matrix的setPolyToPoly的方法。
 *      public boolean setPolyToPoly(float[] src, int srcIndex,  float[] dst, int dstIndex,int pointCount)
 * 简单看一下该方法的参数，src代表变换前的坐标；dst代表变换后的坐标；
 * 从src到dst的变换，可以通过srcIndex和dstIndex来制定第一个变换的点，
 * 一般可能都设置位0。pointCount代表支持的转换坐标的点数，最多支持4个。
 */
public class MatrixPolyToPolyActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PolyToPolyView(this));
    }

    class PolyToPolyView extends View{
        private Bitmap mBitmap;
        private Matrix mMatrix;
        //阴影相关
        private Paint mShadowPaint;
        private Matrix mShadowGradientMatrix;//阴影梯度变化
        private LinearGradient mShadowGradientShader;

        public PolyToPolyView(Context context) {
            super(context);
            mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.folding_img);
            mMatrix = new Matrix();
            //src就是正常情况下图片的4个顶点
            float[] src = {0,0, mBitmap.getWidth(),0, mBitmap.getWidth(),mBitmap.getHeight(), 0,mBitmap.getHeight()};
            //dst将图片右侧两个点的y坐标做了些许的修改
            float[] dst = {0,0, mBitmap.getWidth(),100, mBitmap.getWidth(),mBitmap.getHeight()-100, 0,mBitmap.getHeight()};
            mMatrix.setPolyToPoly(src, 0, dst, 0, src.length>>1);

            /*阴影相关*/
            mShadowPaint = new Paint();
            mShadowPaint.setStyle(Paint.Style.FILL);
            //起点（0，0）、终点（0.5f，0）；颜色从和BLACK到透明；模式为CLAMP，也就是拉伸最后一个像素。
            mShadowGradientShader = new LinearGradient(0,0,0.5f,0,Color.BLACK,Color.TRANSPARENT, Shader.TileMode.CLAMP);
            mShadowPaint.setShader(mShadowGradientShader);

            mShadowGradientMatrix = new Matrix();
            //将横坐标扩大了mBitmap.getWidth()倍，也就是说现在设置渐变的区域为（0.5f*mBitmap.getWidth()，0）半张图的大小
            mShadowGradientMatrix.setScale(mBitmap.getWidth(), 1);
            mShadowGradientShader.setLocalMatrix(mShadowGradientMatrix);
            mShadowPaint.setAlpha((int) (0.9*255));
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.save();
            //绘制背景
            Paint paint = new Paint();
            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(0,0,getWidth(),getHeight(),paint);
            //绘制图片
//            canvas.drawBitmap(mBitmap, mMatrix, null);
            canvas.concat(mMatrix);
            canvas.drawBitmap(mBitmap, 0, 0, null);
            //绘制阴影
            canvas.drawRect(0, 0, mBitmap.getWidth(), mBitmap.getHeight(), mShadowPaint);

            canvas.restore();
        }
    }
}
