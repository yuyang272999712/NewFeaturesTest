package com.yuyang.fitsystemwindowstestdrawer.androidL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Palette取色
 */
public class PaletteActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette_theme);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageView = (ImageView) findViewById(R.id.imageView);

        setSupportActionBar(toolbar);
        toolbar.setTitle("Palette更改色调");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.a);
        final Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.b);

        imageView.setImageBitmap(bitmap1);

        Palette.Builder builder = Palette.from(bitmap1);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Log.e("PaletteActivity--", "onGenerated");
                //通过palette来获取对应的着色器
                Palette.Swatch vibrant = palette.getLightVibrantSwatch();
                //将颜色设置给相应的组件
                toolbar.setBackgroundColor(vibrant.getRgb());
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageBitmap(bitmap2);
                Palette palette = Palette.from(bitmap2).generate();
                //通过palette来获取对应的着色器
                Palette.Swatch vibrant = palette.getLightVibrantSwatch();
                //将颜色设置给相应的组件
                toolbar.setBackgroundColor(vibrant.getRgb());
            }
        });
    }
}
