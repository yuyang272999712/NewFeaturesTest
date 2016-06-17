package com.yuyang.fitsystemwindowstestdrawer.notificationDialogPopupWindowToast.dialogAbout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 使用DialogFragment"至少"需要实现onCreateView或者onCreateDialog方法。
 * onCreateView即使用定义的xml布局文件展示Dialog。onCreateDialog即利用AlertDialog或者Dialog创建出Dialog。
 */
public class LoginDialogFragment extends DialogFragment {
    private EditText mUsername;
    private EditText mPassword;

    public interface LoginInputListener {
        void onLoginInputComplete(String username, String password);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_layout_login, null);
        mUsername = (EditText) view.findViewById(R.id.id_txt_username);
        mPassword = (EditText) view.findViewById(R.id.id_txt_password);
        builder.setView(view)
                .setPositiveButton("登陆", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginInputListener listener = (LoginInputListener) getActivity();
                        listener.onLoginInputComplete(mUsername.getText().toString(),
                                mPassword.getText().toString());
                    }
                })
                .setNegativeButton("取消", null);
        return builder.create();
    }
}
