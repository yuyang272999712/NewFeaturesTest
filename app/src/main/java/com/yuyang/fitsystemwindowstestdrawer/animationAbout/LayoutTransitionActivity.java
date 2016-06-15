package com.yuyang.fitsystemwindowstestdrawer.animationAbout;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 布局动画管理器LayoutTransition\LayoutAnimationController
 */
public class LayoutTransitionActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private CheckBox appearing;
    private CheckBox change_appearing;
    private CheckBox disappearing;
    private CheckBox change_disappearing;
    private GridLayout container;
    private LinearLayout baseLayout;

    private int textValue = 1;
    //布局动画管理器
    private LayoutTransition transition;
    //布局动画管理器
    private LayoutAnimationController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_transition);

        appearing = (CheckBox) findViewById(R.id.layout_appearing);
        change_appearing = (CheckBox) findViewById(R.id.layout_change_appearing);
        disappearing = (CheckBox) findViewById(R.id.layout_disappearing);
        change_disappearing = (CheckBox) findViewById(R.id.layout_change_disappearing);
        container = (GridLayout) findViewById(R.id.layout_container);
        baseLayout = (LinearLayout) findViewById(R.id.base_layout);

        appearing.setOnCheckedChangeListener(this);
        change_appearing.setOnCheckedChangeListener(this);
        disappearing.setOnCheckedChangeListener(this);
        change_disappearing.setOnCheckedChangeListener(this);

        //默认动画全部开启
        transition = new LayoutTransition();
        container.setLayoutTransition(transition);

        //TODO yuyang 代码设置容器出现的动画
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_in_animation);
//        controller = new LayoutAnimationController(animation);
//        controller.setOrder(LayoutAnimationController.ORDER_RANDOM);
//        baseLayout.setLayoutAnimation(controller);
    }

    public void addView(View view){
        Button button = new Button(this);
        button.setText(""+textValue++);
        container.addView(button, 0);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.removeView(v);
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        transition = new LayoutTransition();
        //当一个View在ViewGroup中出现时，对此View设置的动画
        //TODO yuyang 使用默认LayoutTransition动画
//        transition.setAnimator(LayoutTransition.APPEARING,
//                (appearing.isChecked() ? transition.getAnimator(LayoutTransition.APPEARING):null));
        //TODO yuyang 自定义LayoutTransition动画
        transition.setAnimator(LayoutTransition.APPEARING,
                (appearing.isChecked() ? ObjectAnimator.ofFloat(this, "scaleX", 0, 1):null));
        //当一个View在ViewGroup中出现时，对此View对其他View位置造成影响，对其他View设置的动画
        transition.setAnimator(LayoutTransition.CHANGE_APPEARING,
                (change_appearing.isChecked() ? transition.getAnimator(LayoutTransition.CHANGE_APPEARING):null));
        //当一个View在ViewGroup中消失时，对此View设置的动画
        transition.setAnimator(LayoutTransition.DISAPPEARING,
                (disappearing.isChecked() ? transition.getAnimator(LayoutTransition.DISAPPEARING):null));
        //当一个View在ViewGroup中消失时，对此View对其他View位置造成影响，对其他View设置的动画
        transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING,
                (change_disappearing.isChecked() ? transition.getAnimator(LayoutTransition.CHANGE_DISAPPEARING):null));
        container.setLayoutTransition(transition);
    }
}
