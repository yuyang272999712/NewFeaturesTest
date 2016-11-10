package com.yuyang.fitsystemwindowstestdrawer.metricsAbout.textFontMetrics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 文字绘制
 */

public class TextFontMetricsActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView fontMetricsInfo;
    private TextMetricsView textMetricsView;
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_font_metrics);

        setToolbar();

        fontMetricsInfo = (TextView) findViewById(R.id.fontMetricsInfo);
        textMetricsView = (TextMetricsView) findViewById(R.id.fontMetricsText);
        editText = (EditText) findViewById(R.id.edit_text);

        fontMetricsInfo.setText("top："+textMetricsView.getFontTop() + "\n"
                +"ascent："+textMetricsView.getFontAscent() + "\n"
                +"descent："+textMetricsView.getFontDescent() + "\n"
                +"bottom："+textMetricsView.getFontBottom() + "\n"
                +"leading："+textMetricsView.getFontLeading());
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("文字测量相关");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setText(View view){
        String text = editText.getText().toString();
        textMetricsView.setText(text);
    }
}
