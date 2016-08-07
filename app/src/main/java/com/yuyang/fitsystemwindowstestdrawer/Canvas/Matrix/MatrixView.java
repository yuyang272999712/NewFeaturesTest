package com.yuyang.fitsystemwindowstestdrawer.Canvas.Matrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 变形矩阵
 */
public class MatrixView extends View {
    Bitmap bitmap;
    BIANHUAN_TYPE type = BIANHUAN_TYPE.NORMAL;
    Matrix matrix = new Matrix();

    private enum BIANHUAN_TYPE{
        NORMAL,
        SCALE,
        TRANSLATE,
        ROTATE,
        SKEW
    }

    public MatrixView(Context context) {
        this(context, null);
    }

    public MatrixView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatrixView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(type == BIANHUAN_TYPE.NORMAL){
            canvas.drawBitmap(bitmap, matrix, null);
        }else if (type == BIANHUAN_TYPE.SCALE){
            matrix.setScale(2, 2);
            canvas.drawBitmap(bitmap, matrix, null);
        }else if (type == BIANHUAN_TYPE.TRANSLATE){
            matrix.setTranslate(200, 200);
            canvas.drawBitmap(bitmap, matrix, null);
        }else if (type == BIANHUAN_TYPE.ROTATE){
            matrix.setRotate(90, bitmap.getWidth()/2, bitmap.getHeight()/2);
            canvas.drawBitmap(bitmap, matrix, null);
        }else if (type == BIANHUAN_TYPE.SKEW){
            matrix.setSkew(2, 4);
            canvas.drawBitmap(bitmap, matrix, null);
        }
    }

    public void scale(){
        type = BIANHUAN_TYPE.SCALE;
        invalidate();
    }

    public void translate(){
        type = BIANHUAN_TYPE.TRANSLATE;
        invalidate();
    }

    public void rotate(){
        type = BIANHUAN_TYPE.ROTATE;
        invalidate();
    }

    public void skew(){
        type = BIANHUAN_TYPE.SKEW;
        invalidate();
    }

    public void reset(){
        type = BIANHUAN_TYPE.NORMAL;
        matrix = new Matrix();
        invalidate();
    }

    public void setMatrix(float[] matrixValue) {
        matrix.setValues(matrixValue);
        invalidate();
    }
}
