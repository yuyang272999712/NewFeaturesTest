package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.DefinedKeyBoard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 自定义键盘
 */

public class DefinedKeyBoardActivity extends AppCompatActivity {
    private EditText mEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defined_keyboard);

        mEditText = (EditText) findViewById(R.id.edit_text);
        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefinedKeyboard keyboard = new DefinedKeyboard(DefinedKeyBoardActivity.this, mEditText);
                keyboard.show();
            }
        });
    }
}
