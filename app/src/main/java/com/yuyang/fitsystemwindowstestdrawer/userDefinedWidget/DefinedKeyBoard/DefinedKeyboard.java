package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.DefinedKeyBoard;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Pop弹窗式的自定义键盘
 */

public class DefinedKeyboard {
    private PopupWindow mPopWindow;
    private KeyboardView mKeyBoardView;
    private Keyboard mNumKeyboard;//数字键盘
    private Keyboard mLetKeyboard;//字母键盘

    private boolean isNumMode = true;
    private boolean isUpper = false;// 是否大写
    private Activity mActivity;

    public DefinedKeyboard(Activity context, final EditText editText){
        this.mActivity = context;

        mPopWindow = new PopupWindow();
        View popView = LayoutInflater.from(context).inflate(R.layout.pop_keyboard, null);
        mPopWindow.setContentView(popView);
        mPopWindow.setTouchable(true);
        mPopWindow.setFocusable(true);
        mPopWindow.setBackgroundDrawable(new ColorDrawable());
        mPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setAnimationStyle(R.style.PopWindowStyle);

        mKeyBoardView = (KeyboardView) popView.findViewById(R.id.key_board_view);
        mKeyBoardView.setPreviewEnabled(false);
        mNumKeyboard = new Keyboard(context, R.xml.keyboard_number);
        mLetKeyboard = new Keyboard(context, R.xml.keyboard_letter);

        mKeyBoardView.setKeyboard(mNumKeyboard);

        mKeyBoardView.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int primaryCode) {

            }

            @Override
            public void onRelease(int primaryCode) {

            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                Editable editable = editText.getText();
                int start = editText.getSelectionStart();
                if (primaryCode == Keyboard.KEYCODE_DELETE){//删除键
                    if (editable != null && editable.length()>0 && start>0){
                        editable.delete(start - 1, start);
                    }
                }else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE){//数字/字母键盘切换
                    if (isNumMode){
                        mKeyBoardView.setKeyboard(mLetKeyboard);
                        isNumMode = false;
                    }else {
                        mKeyBoardView.setKeyboard(mNumKeyboard);
                        isNumMode = true;
                    }
                }else if (primaryCode == Keyboard.KEYCODE_SHIFT){//大小写切换
                    isUpper = !isUpper;
                    mLetKeyboard.setShifted(isUpper);
                    for (Keyboard.Key mKey:mLetKeyboard.getKeys()) {
                        if ("大写".equals(mKey.label) && isUpper) {
                            mKey.label = "小写";
                            break;
                        } else if ("小写".equals(mKey.label) && !isUpper) {
                            mKey.label = "大写";
                            break;
                        }
                    }
                    mKeyBoardView.invalidateAllKeys();
                }else if (primaryCode == Keyboard.KEYCODE_DONE){//完成
                    hide();
                }else {
                    String str = Character.toString((char) primaryCode);
                    if (isWord(str)) {
                        if (isUpper) {
                            str = str.toUpperCase();
                        } else {
                            str = str.toLowerCase();
                        }
                    }
                    editable.insert(start, str);
                }
            }

            @Override
            public void onText(CharSequence text) {

            }

            @Override
            public void swipeLeft() {

            }

            @Override
            public void swipeRight() {

            }

            @Override
            public void swipeDown() {

            }

            @Override
            public void swipeUp() {

            }
        });
    }

    private boolean isWord(String str) {
        return str.matches("[a-zA-Z]");
    }

    protected void show(){
        mPopWindow.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    protected void hide(){
        mPopWindow.dismiss();
    }
}
