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
 * 初步实现折叠
 * <p/>
 * 妹子折叠成了8份，且阴影的范围为：每个沉下去夹缝的左右两侧，左侧黑色半透明遮盖，右侧短距离的黑色到透明阴影（大家可以仔细看）。
 * 现在其实大家以及会将图片简单倾斜和添加阴影了，那么唯一的难点就是怎么将一张图分成很多快，我相信每块的折叠大家都会。
 * 其实我们可以通过绘制该图多次，比如第一次绘制往下倾斜；第二次绘制网上倾斜；这样就和SimpleUseActivity.jpeg的实现类似了，只需要利用setPolyToPoly。
 * 那么绘制多次，每次显示肯定不是一整张图，比如第一次，我只想显示第一块，所以我们还需要clipRect的配合
 */
public class SimpleUseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PolyToPolyView(this));
    }

    class PolyToPolyView extends View {
        private static final int NUM_OF_POINT = 8;
        /**
         * 折叠后宽度与原图宽度比例
         */
        private float mFactor = 0.8f;
        /**
         * 折叠后宽度
         */
        private int mTranslateDis;
        /**
         * 折叠块数
         */
        private int mNumOfFolds = NUM_OF_POINT;

        private Matrix[] mMatrices = new Matrix[mNumOfFolds];
        private Bitmap mBitmap;
        /**
         * 绘制黑色透明区域
         */
        private Paint mSolidPaint;
        /**
         * 绘制阴影
         */
        private Paint mShadowPaint;
        private Matrix mShadowGradientMatrix;
        private LinearGradient mShadowGradientShader;
        /**
         * 原图每块宽度
         */
        private int mFlodWidth;
        /**
         * 折叠时每块宽度
         */
        private int mTranslateDisPerFlod;

        public PolyToPolyView(Context context) {
            super(context);
            mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.folding_img);
            //折叠后宽度
            mTranslateDis = (int) (mBitmap.getWidth() * mFactor);
            //原图每块宽度
            mFlodWidth = mBitmap.getWidth() / mNumOfFolds;
            //折叠时每块宽度
            mTranslateDisPerFlod = mTranslateDis / mNumOfFolds;
            //初始化mMatrices
            for (int i = 0; i < mMatrices.length; i++) {
                mMatrices[i] = new Matrix();
            }

            mSolidPaint = new Paint();
            int alpha = (int) (255 * mFactor * 0.8f);
            mSolidPaint.setColor(Color.argb((int) (alpha * 0.8F), 0, 0, 0));
            //初始化阴影
            mShadowPaint = new Paint();
            mShadowPaint.setStyle(Paint.Style.FILL);
            mShadowGradientMatrix = new Matrix();
            mShadowGradientMatrix.setScale(mFlodWidth, 1);
            mShadowGradientShader = new LinearGradient(0,0,0.5f,0,Color.BLACK,Color.TRANSPARENT, Shader.TileMode.CLAMP);
            mShadowGradientShader.setLocalMatrix(mShadowGradientMatrix);
            mShadowPaint.setShader(mShadowGradientShader);
            mShadowPaint.setAlpha(alpha);

            //纵轴减小的高度，用勾股定理计算
            int depth = (int)Math.sqrt(mFlodWidth*mFlodWidth - mTranslateDisPerFlod*mTranslateDisPerFlod)/2;
            //转换点
            float[] src = new float[mNumOfFolds];
            float[] dst = new float[mNumOfFolds];
            /**
             * 原图的每一块，对应折叠后的每一块，方向为左上、右上、右下、左下，大家在纸上自己画下
             */
            for (int i=0; i<mNumOfFolds; i++){
                src[0] = i*mFlodWidth;
                src[1] = 0;
                src[2] = src[0] + mFlodWidth;
                src[3] = 0;
                src[4] = src[2];
                src[5] = mBitmap.getHeight();
                src[6] = src[0];
                src[7] = src[5];

                boolean isEven = i%2==0;//波峰波谷
                dst[0] = i*mTranslateDisPerFlod;
                dst[1] = isEven ? 0 : depth;
                dst[2] = dst[0] + mTranslateDisPerFlod;
                dst[3] = isEven ? depth:0;
                dst[4] = dst[2];
                dst[5] = isEven ? mBitmap.getHeight()-depth:mBitmap.getHeight();
                dst[6] = dst[0];
                dst[7] = isEven ? mBitmap.getHeight():mBitmap.getHeight()-depth;

                mMatrices[i].setPolyToPoly(src, 0, dst, 0, src.length>>1);
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //绘制mNumOfFolds次
            for (int i=0; i<mNumOfFolds; i++){
                canvas.save();
                //将matrix应用到canvas
                canvas.concat(mMatrices[i]);
                //控制显示的大小
                canvas.clipRect(i*mFlodWidth, 0, mFlodWidth*i+mFlodWidth, mBitmap.getHeight());
                //绘制图片
                canvas.drawBitmap(mBitmap, 0, 0, null);
                //移动绘制阴影
                canvas.translate(mFlodWidth*i, 0);
                if (i % 2 == 0) {
                    //绘制黑色遮盖
                    canvas.drawRect(0, 0, mFlodWidth, mBitmap.getHeight(),
                            mSolidPaint);
                }else {
                    //绘制阴影
                    canvas.drawRect(0, 0, mFlodWidth, mBitmap.getHeight(),
                            mShadowPaint);
                }
                canvas.restore();
            }
        }
    }
}
