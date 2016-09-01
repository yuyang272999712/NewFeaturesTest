package com.yuyang.fitsystemwindowstestdrawer.softInput.softInputMode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 软键盘显示管理2
 */
public class SoftInputModeActivity2 extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView context;
    private EditText editText;
    private Button button;

    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        findViews();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMethodManager.toggleSoftInput(0, 0);
            }
        });
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        context = (TextView) findViewById(R.id.context);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        toolbar.setTitle("软键盘Mode_2");
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
