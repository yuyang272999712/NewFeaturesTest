package com.yuyang.network.volley.baseView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yuyang.network.R;

/**
 * Created by yuyang on 2017/4/10.
 */

public class BaseFragment extends Fragment {
    public DialogFragment loading;
    public String tag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loading = new LoadingDialogFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //NetworkManager.getInstance(getActivity().getApplicationContext()).cancel();
    }

    /**
     * 提示信息弹框
     */
    public void showDialog(String info) {
        showDialog(null, info, "我知道了", null, null, null);
    }

    /**
     * 弹出框
     */
    protected void showDialog(String content, String confirm, String cancel, DialogInterface.OnClickListener listener) {
        showDialog(null, content, confirm, cancel, listener, null);
    }

    private Dialog mDialog;

    /**
     * 弹出对话框
     */
    protected void showDialog(String title, String content, String confirm, String cancel, final DialogInterface.OnClickListener confirmListener, final DialogInterface.OnClickListener cancelListener) {
        if (getActivity() == null) {
            return;
        }
        if (mDialog == null) {
            mDialog = new Dialog(getActivity());
        }
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        View view = View.inflate(getActivity(), R.layout.ld_layout_custom_dialog, null);
        TextView titleTxt = (TextView) view.findViewById(R.id.dialog_title);
        TextView contentTxt = (TextView) view.findViewById(R.id.dialog_content);
        TextView cancelTxt = (TextView) view.findViewById(R.id.left_btn);
        TextView confirmTxt = (TextView) view.findViewById(R.id.right_btn);
        View line3 = view.findViewById(R.id.ld_custom_dialog_line3);
        // 设置Title
        if (TextUtils.isEmpty(title)) {
            titleTxt.setVisibility(View.GONE);
        } else {
            titleTxt.setText(title);
            titleTxt.setVisibility(View.VISIBLE);
        }
        // 设置Content
        contentTxt.setText(content);
        // 设置取消按钮
        if (TextUtils.isEmpty(cancel)) {
            cancelTxt.setVisibility(View.GONE);
            line3.setVisibility(View.GONE);
        } else {
            cancelTxt.setVisibility(View.VISIBLE);
            line3.setVisibility(View.VISIBLE);
            cancelTxt.setText(cancel);
        }
        // 设置确定按钮
        if (TextUtils.isEmpty(confirm)) {
            confirmTxt.setVisibility(View.GONE);
        } else {
            confirmTxt.setVisibility(View.VISIBLE);
            confirmTxt.setText(confirm);
        }
        // 设置监听
        confirmTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                if (confirmListener != null) {
                    confirmListener.onClick(mDialog, Dialog.BUTTON_POSITIVE);
                }
            }
        });

        cancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                if (cancelListener != null) {
                    cancelListener.onClick(mDialog, Dialog.BUTTON_NEGATIVE);
                }
            }
        });

        mDialog.setContentView(view);
        mDialog.show();
    }
}
