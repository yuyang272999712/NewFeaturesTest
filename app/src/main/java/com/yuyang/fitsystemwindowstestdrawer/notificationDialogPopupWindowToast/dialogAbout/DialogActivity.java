package com.yuyang.fitsystemwindowstestdrawer.notificationDialogPopupWindowToast.dialogAbout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Dialog相关知识
 * google不推荐直接使用Dialog创建对话框
 *
 * 一、DialogFragment
 *  DialogFragment在android 3.0时被引入。是一种特殊的Fragment，用于在Activity的内容之上展示一个模态的对话框。典型的用于：展示警告框，输入框，确认框等等。
 *  在DialogFragment产生之前，我们创建对话框：一般采用AlertDialog和Dialog。注：官方不推荐直接使用Dialog创建对话框。
 *  使用DialogFragment来管理对话框，当旋转屏幕和按下后退键时可以更好的管理其声明周期，它和Fragment有着基本一致的声明周期。
 *  且DialogFragment也允许开发者把Dialog作为内嵌的组件进行重用，类似Fragment（可以在大屏幕和小屏幕显示出不同的效果）。
 *  使用DialogFragment至少需要实现onCreateView或者onCreateDIalog方法。
 *      onCreateView即使用定义的xml布局文件展示Dialog。
 *      onCreateDialog即利用AlertDialog或者Dialog创建出Dialog。
 * ****传统的new AlertDialog在屏幕旋转时，第一不会保存用户输入的值，第二还会报异常，因为Activity销毁前不允许对话框未关闭。*****
 * ****而通过DialogFragment实现的对话框则可以完全不必考虑旋转的问题。(但必须为每个要保存状态的控价添加ID)*****
 *
 */
public class DialogActivity extends AppCompatActivity implements LoginDialogFragment.LoginInputListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_about);
    }

    /**
     * 使用DialogFragment实现提示
     * @param view
     */
    public void startNormalDialogFragment(View view){
        EditDialogFragment dialogFragment = new EditDialogFragment();
        dialogFragment.show(getFragmentManager(), "EditDialogFragment");
    }
    /**
     * 使用DialogFragment实现提示 重写onCreateDialog
     * @param view
     */
    public void startDialogFragment(View view){
        LoginDialogFragment dialogFragment = new LoginDialogFragment();
        dialogFragment.show(getFragmentManager(), "LoginDialogFragment");
    }

    /**
     * LoginDialogFragment 的处理回调
     * @param username
     * @param password
     */
    @Override
    public void onLoginInputComplete(String username, String password) {
        Toast.makeText(this, "帐号：" + username + ",  密码 :" + password,
                Toast.LENGTH_SHORT).show();
    }
}
