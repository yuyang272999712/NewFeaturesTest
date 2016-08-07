package com.yuyang.fitsystemwindowstestdrawer.Canvas.ColorMatrix;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * ColorMatrix 直接修改矩阵的各项数值来修改图片的样式
 */
public class ColorMatrixValueSetActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ImageView mImageView;
    private GridLayout mGroup;
    private Bitmap bitmap;
    private int mEtWidth, mEtHeight;
    private EditText[] mEts = new EditText[20];//保存矩阵上的EditText
    private float[] mColorMatrix = new float[20];//保存矩阵的值

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_matrix);
        findViews();

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.color_matrix_test1);
        mImageView.setImageBitmap(bitmap);
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mGroup = (GridLayout) findViewById(R.id.group);

        mToolbar.setTitle("ColorMatrix值修改");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mGroup.post(new Runnable() {
            @Override
            public void run() {
                mEtHeight = mGroup.getHeight()/4;
                mEtWidth = mGroup.getWidth()/5;
                addEts();
                initMatrix();
            }
        });
    }

    //作用矩阵效果
    public void btnChange(View view) {
        getMatrix();
        setImageMatrix();
    }

    //重置矩阵效果
    public void btnReset(View view) {
        initMatrix();
        getMatrix();
        setImageMatrix();
    }

    /**
     * 初始化所有EditText的值，即初始化矩阵的值
     */
    private void initMatrix() {
        for (int i=0; i<20; i++){
            if (i%6 == 0){
                mEts[i].setText(String.valueOf(1));
            }else {
                mEts[i].setText(String.valueOf(0));
            }
        }
    }

    /**
     * 向GridLayout中添加EditText
     */
    private void addEts() {
        for (int i=0; i<20; i++){
            EditText editText = new EditText(this);
            mEts[i] = editText;
            mGroup.addView(editText, mEtWidth, mEtHeight);
        }
    }

    /**
     * 获取矩阵的值
     */
    private void getMatrix(){
        for (int i=0; i<20; i++){
            mColorMatrix[i] = Float.valueOf(mEts[i].getText().toString());
        }
    }

    /**
     * 将矩阵值设置到图像
     */
    private void setImageMatrix(){
        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(mColorMatrix);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        mImageView.setImageBitmap(bmp);
    }

}