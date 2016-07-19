package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.stereoView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.ToastUtils;

/**
 * 立体上下循环翻页
 */
public class StereoViewActivity extends AppCompatActivity {
    private Button nextPageBtn;
    private StereoView textsStereo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stereo_view);
        findViews();

        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textsStereo.toNext();
            }
        });
        textsStereo.setiStereoListener(new StereoView.IStereoListener() {
            @Override
            public void toPre(int curScreen) {
                ToastUtils.showShort(StereoViewActivity.this, "翻到了上一页："+curScreen+"页");
            }

            @Override
            public void toNext(int curScreen) {
                ToastUtils.showShort(StereoViewActivity.this, "翻到了下一页："+curScreen+"页");
            }
        });
    }

    private void findViews() {
        nextPageBtn = (Button) findViewById(R.id.stereoView_next_page);
        textsStereo = (StereoView) findViewById(R.id.stereoView_texts);
    }
}
