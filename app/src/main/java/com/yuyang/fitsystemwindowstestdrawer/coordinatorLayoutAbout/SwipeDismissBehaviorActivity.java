package com.yuyang.fitsystemwindowstestdrawer.coordinatorLayoutAbout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 16/5/6.
 */
public class SwipeDismissBehaviorActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_dismiss_behavior);

        textView = (TextView) findViewById(R.id.swipe_text1);

        SwipeDismissBehavior behavior = new SwipeDismissBehavior();
        behavior.setSensitivity(0.1f);
        behavior.setListener(new SwipeDismissBehavior.OnDismissListener() {
            @Override
            public void onDismiss(View view) {
                Toast.makeText(SwipeDismissBehaviorActivity.this,
                        "dismiss",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDragStateChanged(int state) {
                String stateStr = "";
                switch (state){
                    case ViewDragHelper.STATE_DRAGGING:
                        stateStr = "dragging";
                        break;
                    case ViewDragHelper.STATE_IDLE:
                        stateStr = "idle";
                        break;
                    case ViewDragHelper.STATE_SETTLING:
                        stateStr = "settling";
                        break;

                }
                Toast.makeText(SwipeDismissBehaviorActivity.this,
                        stateStr,Toast.LENGTH_SHORT).show();
            }
        });

        CoordinatorLayout.LayoutParams coordinatorParams = (CoordinatorLayout.LayoutParams) textView.getLayoutParams();
        coordinatorParams.setBehavior(behavior);
    }
}
