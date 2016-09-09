package com.yuyang.fitsystemwindowstestdrawer.animationAbout.coinWalletAnimator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.animationAbout.customAnim.Custom3DAnim;

/**
 * 动画实现钱币效果
 */
public class CoinWalletAnimatorActivity extends AppCompatActivity {
    private Button button;
    private ImageView coinImg;
    private ImageView walletImg;

    private AnimationSet coinSet;
    private AnimationSet walletSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_wallet);

        findViews();
        initAnimation();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coinImg.startAnimation(coinSet);
                setWallet();
            }
        });
    }

    private void setWallet() {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(800);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                if (fraction >= 0.9) {
                    valueAnimator.cancel();
                    /*walletImg.setPivotY(0);
                    walletImg.setPivotX(0);
                    walletImg.invalidate();*/
                    ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(walletImg, "scaleX", 1, 1.1f, 0.9f, 1);
                    objectAnimator1.setDuration(600);
                    ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(walletImg, "scaleY", 1, 0.75f, 1.25f, 1);
                    objectAnimator2.setDuration(600);

                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setInterpolator(new LinearInterpolator());
                    animatorSet.playTogether(objectAnimator1, objectAnimator2);
                    animatorSet.start();
                }
            }
        });
        valueAnimator.start();
    }

    private void initAnimation() {
        Animation coinTranslate = AnimationUtils.loadAnimation(this, R.anim.coin_translate_animation);
        Custom3DAnim coin3D = new Custom3DAnim();
        coin3D.setRotateY(360);
        coinSet = new AnimationSet(true);
        coinSet.addAnimation(coin3D);
        coinSet.addAnimation(coinTranslate);
        coinSet.setDuration(800);
        coinSet.setInterpolator(new LinearInterpolator());
    }

    private void findViews() {
        button = (Button) findViewById(R.id.button);
        coinImg = (ImageView) findViewById(R.id.coin);
        walletImg = (ImageView) findViewById(R.id.wallet);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("钱币掉落效果");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
