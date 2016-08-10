package com.yuyang.fitsystemwindowstestdrawer.animationAbout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.animationAbout.customAnim.CustomAnimationActivity;

/**
 * 动画相关
 */
public class AnimationActivity extends AppCompatActivity {

    private Button propertyBtn1;
    private Button propertyBtn2;
    private Button propertyBtn3;
    private Button propertyBtn4;
    private Button viewBtn5;
    private Button drawableBtn6;
    private Button customAnimBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        findViews();
        initAction();
    }

    private void findViews() {
        propertyBtn1 = (Button) findViewById(R.id.animation_property);
        propertyBtn2 = (Button) findViewById(R.id.animation_property_xml);
        propertyBtn3 = (Button) findViewById(R.id.animation_layout_transition);
        propertyBtn4 = (Button) findViewById(R.id.animation_layout_view);
        viewBtn5 = (Button) findViewById(R.id.animation_view);
        drawableBtn6 = (Button) findViewById(R.id.animation_drawable);
        customAnimBtn = (Button) findViewById(R.id.animation_custom);
    }

    private void initAction() {
        propertyBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimationActivity.this, PropertyAniActivity.class);
                startActivity(intent);
            }
        });
        propertyBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimationActivity.this, PropertyAniXmlActivity.class);
                startActivity(intent);
            }
        });
        propertyBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimationActivity.this, LayoutTransitionActivity.class);
                startActivity(intent);
            }
        });
        propertyBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimationActivity.this, ViewAnimateActivity.class);
                startActivity(intent);
            }
        });
        viewBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimationActivity.this, ViewAnimationActivity.class);
                startActivity(intent);
            }
        });
        drawableBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimationActivity.this, AnimationDrawableActivity.class);
                startActivity(intent);
            }
        });
        customAnimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimationActivity.this, CustomAnimationActivity.class);
                startActivity(intent);
            }
        });
    }

}
