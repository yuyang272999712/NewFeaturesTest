package com.yuyang.fitsystemwindowstestdrawer.largeImage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.largeImage.view.LargeImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * 加载超大图
 */
public class LargeImageTestActivity extends AppCompatActivity {
    private LargeImageView largeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image_test);

        largeImageView = (LargeImageView) findViewById(R.id.largeImage);

        try {
            InputStream inputStream = getAssets().open("qm.jpg");
            largeImageView.setInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
