package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.blurredEffect;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.blurredEffect.customView.BlurredView;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.blurredEffect.util.BlurBitmap;

/**
 * 图片模糊效果
 */
public class BlurredImageActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ImageView mImageView;
    private BlurredView mBlurredView;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blurred_image);
        findViews();

        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.img1)).getBitmap();
        //图一直接使用模糊效果
        mImageView.setImageBitmap(BlurBitmap.blur(this, bitmap));

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mBlurredView.setBlurredLevel(((float)progress)/100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("图片模糊效果");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mImageView = (ImageView) findViewById(R.id.blurred_image_view);
        mBlurredView = (BlurredView) findViewById(R.id.blurred_image_custom_view);
        mSeekBar = (SeekBar) findViewById(R.id.seek_bar);
    }
}
