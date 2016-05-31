package com.yuyang.fitsystemwindowstestdrawer.userDefinedViews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 裁剪图片
 */
public class ClipImageResultActivity extends AppCompatActivity {
    private ImageView resultImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_image_result);

        resultImg = (ImageView) findViewById(R.id.clip_result_img);

        byte[] b = getIntent().getByteArrayExtra("bitmap");
        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        if (bitmap != null) {
            resultImg.setImageBitmap(bitmap);
        }
    }

}
