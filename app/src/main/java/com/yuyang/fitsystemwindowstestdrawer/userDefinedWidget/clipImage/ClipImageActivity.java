package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.clipImage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.io.ByteArrayOutputStream;

/**
 * 裁剪图片
 */
public class ClipImageActivity extends AppCompatActivity {
    private ClipImageLayout clipImageLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_image);

        clipImageLayout = (ClipImageLayout) findViewById(R.id.clip_layout);
    }

    /**
     * 点击裁剪图片
     * @param view
     */
    public void clip(View view){
        Bitmap bitmap = clipImageLayout.clip();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] datas = baos.toByteArray();

        Intent intent = new Intent(this, ClipImageResultActivity.class);
        intent.putExtra("bitmap", datas);
        startActivity(intent);
    }
}
