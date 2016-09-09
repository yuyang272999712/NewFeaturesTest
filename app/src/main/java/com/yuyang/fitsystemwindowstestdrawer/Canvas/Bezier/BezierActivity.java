package com.yuyang.fitsystemwindowstestdrawer.Canvas.Bezier;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 贝塞尔曲线
 */
public class BezierActivity extends AppCompatActivity {
    private Bezier2View bezier2View;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);

        bezier2View = (Bezier2View) findViewById(R.id.bezier2View);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("贝塞尔曲线");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("重置");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        bezier2View.reset();
        return true;
    }
}
