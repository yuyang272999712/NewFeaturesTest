package com.yuyang.fitsystemwindowstestdrawer.Canvas.Matrix;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * ColorMatrix设置图片色调、饱和度、亮度
 */
public class ImageHelper {
    public static Bitmap handleImageEffect(Bitmap bitmap, float mHue, float mSaturation, float mLum) {
        //TODO yuyang android系统不允许直接修改原图，因此必须通过原图创建一个同样大小的Bitmap，并将原图绘制到该Bitmap中，
        //              以一个副本的形式来修改图像
        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();

        ColorMatrix hueMatrix = new ColorMatrix();//色调
        hueMatrix.setRotate(0, mHue);
        hueMatrix.setRotate(1, mHue);
        hueMatrix.setRotate(2, mHue);

        ColorMatrix saturationMatrix = new ColorMatrix();//饱和度
        saturationMatrix.setSaturation(mSaturation);

        ColorMatrix lumMatrix = new ColorMatrix();//亮度
        lumMatrix.setScale(mLum, mLum, mLum, 1);

        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(hueMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(lumMatrix);

        //TODO yuyang Paint的setColorFilter请注意
        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bmp;
    }
}
