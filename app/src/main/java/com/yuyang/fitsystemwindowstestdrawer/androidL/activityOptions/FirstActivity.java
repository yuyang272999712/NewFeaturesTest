package com.yuyang.fitsystemwindowstestdrawer.androidL.activityOptions;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * android5界面切换效果
 * TODO yuyang Activity transition动画
 *  Activity transition API围绕退出（exit），进入（enter），返回（return）和再次进入（reenter）四种transition。按照上面对A和B的约定，我这样描述这一过程。
        Activity A的退出变换（exit transition）决定了在A调用B的时候，A中的View是如何播放动画的。
        Activity B的进入变换（enter transition）决定了在A调用B的时候，B中的View是如何播放动画的。
        Activity B的返回变换（return transition）决定了在B返回A的时候，B中的View是如何播放动画的。
        Activity A的再次进入变换（reenter transition）决定了在B返回A的时候，A中的View是如何播放动画的。

 *  在代码中触发通过finishAfterTransition()方法触发返回动画，而不是调用finish()方法。
 *
 *  默认情况下，material主题的应用中enter/return的content transition会在exit/reenter的content transitions结束之前开始播放（只是稍微早于），
 * 这样会看起来更加连贯。如果你想明确屏蔽这种行为，可以调用setWindowAllowEnterTransitionOverlap() 和 setWindowAllowReturnTransitionOverlap()方法。
 *
 * 第一个Activity
 */
public class FirstActivity extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_first);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void explode(View view){
        intent = new Intent(this, SecondActivity.class);
        intent.putExtra("flag", 0);
        startActivity(intent,  ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void slide(View view){
        intent = new Intent(this, SecondActivity.class);
        intent.putExtra("flag", 1);
        startActivity(intent,  ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void fade(View view){
        intent = new Intent(this, SecondActivity.class);
        intent.putExtra("flag", 2);
        startActivity(intent,  ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void share(View view){
        View fab = findViewById(R.id.fab_button);
        intent = new Intent(this, SecondActivity.class);
        intent.putExtra("flag", 3);
        startActivity(intent,  ActivityOptions.makeSceneTransitionAnimation(this,
                Pair.create(view, "share"),
                Pair.create(fab, "fab")).toBundle());
    }
}
