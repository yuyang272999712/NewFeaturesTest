package com.yuyang.fitsystemwindowstestdrawer.notificationDialogPopupWindowToast.dialogAbout;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 创建自己的Dialog
 */
public class MyDialog {
    protected Context context;
    protected LayoutInflater inflater;
    private Dialog dialog;
    private Button positiveBtn, negativeBtn;
    public TextView mPromptText;
    private NegativeListener negativeListener;
    private PositiveListener positiveListener;

    /** 确定默认监听 */
    private final View.OnClickListener PositiveDefaultListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (positiveListener != null){
                positiveListener.positive();
            }
            dismiss();
        }
    };

    /** 取消默认监听 */
    private final View.OnClickListener NegativeDefaultListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (negativeListener != null){
                negativeListener.negative();
            }
            dismiss();
        }
    };

    public MyDialog(Context context) {
        this(context, R.style.MyDialogStyle);
    }

    public MyDialog(Context context, int dialogTheme){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        dialog = new Dialog(context, dialogTheme);
        initDialogViews();
        setListener();
    }

    private void setListener() {
        positiveBtn.setOnClickListener(PositiveDefaultListener);
        negativeBtn.setOnClickListener(NegativeDefaultListener);
        //TODO yuyang 点击dialog外部区域不可取消
        dialog.setCanceledOnTouchOutside(false);
    }

    private void initDialogViews() {
        View contextView = inflater.inflate(R.layout.dialog_user_custom, null);
        positiveBtn = (Button) contextView.findViewById(R.id.dialog_positive);
        negativeBtn = (Button) contextView.findViewById(R.id.dialog_negative);
        mPromptText = (TextView) contextView.findViewById(R.id.promptText);
        //ZHU yuyang 固定dialog内容宽度
        int width = context.getResources().getDisplayMetrics().widthPixels;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) (width*0.8),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.addContentView(contextView, params);
    }

    /**
     * 显示对话框
     */
    public synchronized void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * 关闭对话框
     */
    public synchronized void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 设置是否能被back键关闭。默认是true
     * @param cancelable
     */
    public synchronized void setCancelable(boolean cancelable) {
        if (dialog != null) {
            dialog.setCancelable(cancelable);
        }
    }

    /**
     * 设置提示信息
     */
    public void setPromptText(String text) {
        if (text == null) {
            return;
        }
        mPromptText.setText(text);
    }

    /**
     * 设置取消键事件
     * @param listener
     */
    public void setNegativeListener(NegativeListener listener){
        if (listener != null) {
            negativeListener = listener;
        }
    }

    /**
     * 设置确定键事件
     * @param listener
     */
    public void setPositiveListener(PositiveListener listener){
        if (listener != null){
            positiveListener = listener;
        }
    }

    /**
     * 确定键回调函数
     */
    public interface PositiveListener{
        void positive();
    }

    /**
     * 取消键回调函数
     */
    public interface NegativeListener{
        void negative();
    }
}
