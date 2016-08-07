package com.yuyang.fitsystemwindowstestdrawer.Canvas.ColorMatrix;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 像素点分析修改图像色值
 * 通过 bitmap.getPixels(oldPx, 0, width, 0, 0, width, height);
 * 获取图像每一个像素点的色值，再逐个对每一个像素点做处理
 */
public class PixelsEffectActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ImageView imageView1, imageView2, imageView3, imageView4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pixels_effect);
        findViews();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.color_matrix_test2);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);

        imageView1.setImageBitmap(bitmap);
        imageView2.setImageBitmap(ImageHelper.handleImageNegative(bitmap));
        imageView3.setImageBitmap(ImageHelper.handleImagePixelsOldPhoto(bitmap));
        imageView4.setImageBitmap(ImageHelper.handleImagePixelsRelief(bitmap));
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("像素点分析修改图像色值");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
