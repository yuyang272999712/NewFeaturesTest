package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.colorPicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 色值选择器
 */
public class ColorPickerActivity extends AppCompatActivity {
    private Button pickerBtn;
    private ColorPicker colorPicker;
    private MultiColorPicker multiColorPicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);
        findViews();

        pickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog colorPickerDialog = new ColorPickerDialog(ColorPickerActivity.this,
                        0xffcccccc,
                        new ColorPickerDialog.OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int newColor) {
                                pickerBtn.setBackgroundColor(newColor);
                            }
                        });
                colorPickerDialog.show();
            }
        });
    }

    private void findViews() {
        pickerBtn = (Button) findViewById(R.id.color_picker_btn);
        colorPicker = (ColorPicker) findViewById(R.id.color_picker);
        multiColorPicker = (MultiColorPicker) findViewById(R.id.multi_color_picker);
    }
}
