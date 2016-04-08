package com.yuyang.fitsystemwindowstestdrawer.propertyAnimation;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 16/3/22.
 */
public class PropertyAnimationActivity extends AppCompatActivity {

    private ImageView image1;

    private ObjectAnimator objectAni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.property_ani);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        image1 = (ImageView) findViewById(R.id.image1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setObjectAnimation();
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectAni.start();
            }
        });
    }

    private void setObjectAnimation() {
        objectAni = ObjectAnimator.ofFloat(image1,"rotationX",0f, 360f)
                .setDuration(1000);
    }

}
