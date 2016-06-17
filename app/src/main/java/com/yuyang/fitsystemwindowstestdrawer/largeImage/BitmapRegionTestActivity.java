package com.yuyang.fitsystemwindowstestdrawer.largeImage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * BitmapRegionDecoder 图片区域显示实验
 *
 */
public class BitmapRegionTestActivity extends AppCompatActivity {

    ImageView regionView;
    ImageView originView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_region_test);

        regionView = (ImageView) findViewById(R.id.region_view);
        originView = (ImageView) findViewById(R.id.origin_view);

        try {
            InputStream inputStream = getAssets().open("tangyan.jpg");

            originView.setImageBitmap(BitmapFactory.decodeStream(inputStream));
            //获取图片宽高
            BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
            tmpOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, tmpOptions);
            int height = tmpOptions.outHeight;
            int width = tmpOptions.outWidth;
            //设置显示图片的中心区域
            BitmapRegionDecoder bitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = bitmapRegionDecoder.decodeRegion(new Rect(width/2 - 100,height/2 - 100,
                    width/2 + 100,height/2 + 100), options);
            regionView.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
