package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.drawableAbout;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.drawableAbout.RoundImageDrawable;

/**
 * Created by yuyang on 16/6/13.
 */
public class RoundImageDrawableActivity extends Activity {
    private ImageView imageView1;
    private ImageView imageView2;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_drawable);

        imageView1 = (ImageView) findViewById(R.id.my_drawable_one);
        imageView2 = (ImageView) findViewById(R.id.my_drawable_two);
        textView = (TextView) findViewById(R.id.my_drawable_text);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.a);

        imageView1.setImageDrawable(new RoundImageDrawable(bitmap));
        imageView2.setImageDrawable(new RoundImageDrawable(bitmap));
        textView.setBackgroundDrawable(new RoundImageDrawable(bitmap));
    }
}
