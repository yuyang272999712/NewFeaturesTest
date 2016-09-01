package com.yuyang.fitsystemwindowstestdrawer.softInput.emotionMode.utils;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

/**
 * 表情点击监听
 */
public class GlobalOnItemClickManager {
    private static GlobalOnItemClickManager instance;
    private EditText mEditText;

    public static GlobalOnItemClickManager getInstance() {
        if (instance == null) {
            instance = new GlobalOnItemClickManager();
        }
        return instance;
    }

    public void attachToEditText(EditText editText) {
        mEditText = editText;
    }

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String emojiName = (String) parent.getAdapter().getItem(position);//表情名
                // 获取当前光标位置,在指定位置上添加表情图片文本
                int curPosition = mEditText.getSelectionStart();
                StringBuilder sb = new StringBuilder(mEditText.getText().toString());
                sb.insert(curPosition, emojiName);
                // 特殊文字处理,将表情等转换一下
                mEditText.setText(SpanStringUtils.getEmotionContent(mEditText, sb.toString()));
                // 将光标设置到新增完表情的右侧
                mEditText.setSelection(curPosition + emojiName.length());
            }
        };
    }
}
