package com.yuyang.fitsystemwindowstestdrawer.viewPager.galleryViewPager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;

/**
 * 获取有倒影的Bitmap
 */

public class ImageUtil {
    /**
     * 获取带倒影的Bitmap
     * @param imgId
     * @param context
     * @return
     */
    public static Bitmap getReverseBitmapById(int imgId, Context context){
        Bitmap sourceBitmap = BitmapFactory.decodeResource(context.getResources(), imgId);
        Matrix matrix = new Matrix();
        matrix.setScale(1, -1);
        Bitmap reverseBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight(), matrix, true);

        Bitmap canvasBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(), sourceBitmap.getHeight()+sourceBitmap.getHeight()/3+40, sourceBitmap.getConfig());
        Canvas canvas = new Canvas(canvasBitmap);

        canvas.drawBitmap(sourceBitmap, 0, 0, null);
        canvas.drawBitmap(reverseBitmap, 0, sourceBitmap.getHeight()+30, null);

        Paint paint = new Paint();
        paint.setShader(new LinearGradient(0, sourceBitmap.getHeight()+30, 0, canvasBitmap.getHeight(), Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, sourceBitmap.getHeight()+30, sourceBitmap.getWidth(), canvasBitmap.getHeight(), paint);

        return canvasBitmap;
    }
}
