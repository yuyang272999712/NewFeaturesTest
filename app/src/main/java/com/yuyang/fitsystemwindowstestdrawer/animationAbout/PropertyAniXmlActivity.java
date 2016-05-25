package com.yuyang.fitsystemwindowstestdrawer.animationAbout;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 在XML中定义属性动画
 *
 * 在res下animator文件夹中定义属性动画
 */
public class PropertyAniXmlActivity extends AppCompatActivity {
    private ImageView tagertView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation_xml);

        findViews();
    }

    public void simpleAnim(View view){
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.property_animation_1);
        animator.setTarget(tagertView);
        animator.start();
    }

    public void multiAnim(View view){
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.property_animation_2);
        //TODO yuyang 调节view的属性动画中心点
        tagertView.setPivotX(0);
        tagertView.setPivotY(0);
        tagertView.invalidate();

        animator.setTarget(tagertView);
        animator.start();
    }

    private void findViews() {
        tagertView = (ImageView) findViewById(R.id.property_ani_img);
    }
}
