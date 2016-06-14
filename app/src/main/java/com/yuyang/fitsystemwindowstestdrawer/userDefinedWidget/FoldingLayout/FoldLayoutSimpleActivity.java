package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.FoldingLayout;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.FoldingLayout.view.FoldingLayout;

/**
 * FoldingLayout应用测试
 */
public class FoldLayoutSimpleActivity extends Activity {
    private FoldingLayout foldingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fold_layout);

        foldingLayout = (FoldingLayout) findViewById(R.id.folding_layout);

        foldingLayout.post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animator = ObjectAnimator.ofFloat(foldingLayout, "factor", 1, 0, 1);
                animator.setDuration(5000).start();
            }
        });
    }
}
