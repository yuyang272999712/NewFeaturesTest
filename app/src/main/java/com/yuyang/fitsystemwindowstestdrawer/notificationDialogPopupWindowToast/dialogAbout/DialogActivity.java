package com.yuyang.fitsystemwindowstestdrawer.notificationDialogPopupWindowToast.dialogAbout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Dialog相关知识
 * google不推荐直接使用Dialog创建对话框
 * Dialog其实就是WindowManager在原有Window上新加了一个Window，让后将dialog附加到该window中
 *
 * 一、AlertDialog
 *  没什么好说的，主要就是API方法
 *
 * 二、ProgressDialog/DatePickerDialog/TimePickerDialog
 *  也主要是API，自由发挥空间较少
 *
 * 三、DialogFragment
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
 * 四、自定义样式的Dialog
 *  虽然google不建议直接使用Dialog，但是为了界面的美观，我们又是不得不直接继承Dialog类，直接改写其样式。
 *  自定义Dialog样式请见 style.xml中的“MyDialogStyle”
 *
 */
public class DialogActivity extends AppCompatActivity implements LoginDialogFragment.LoginInputListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_about);
    }

    /**
     * 使用AlertDialog 启动警告对话框
     * @param view
     */
    public void startAlertDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置对话框图标
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        //设置标题
        builder.setTitle("警告框");
        //设置文本内容
        builder.setMessage("警告内容");
        //设置按钮事件
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogActivity.this, "点击了取消", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogActivity.this, "点击了确定", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNeutralButton("中立", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogActivity.this, "点击了中立", Toast.LENGTH_SHORT).show();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Toast.makeText(DialogActivity.this, "对话框消失了", Toast.LENGTH_SHORT).show();
                }
            });
        }
        //设置对话框是否可取消（点击外部或者返回键取消）
        builder.setCancelable(false);
        //创建对话框
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 使用AlertDialog 启动列表对话框
     * @param view
     */
    public void startListDialog(View view){
        //定义列表中的选项
        final String[] items = new String[]{
                "关机",
                "重启手机",
                "写遗书",
                "跑路",
                "把手机送给小明"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置列表内容和点击事件 TODO yuyang 也可以使用setAdapter方法自定义列表行样式
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogActivity.this, items[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    /**
     * 使用AlertDialog 启动单选对话框
     * @param view
     */
    public void startRadioDialog(View view){
        //定义单选的选项
        final String[] items = new String[]{
                "地球",
                "塞伯坦",
                "M78星云",
                "氪星"
        };
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("请选择：");
        //设置单选内容和点击事件，也可以通过setMultiChoiceItems方法设置多选
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogActivity.this, items[which], Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogActivity.this, "取消了", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    /**
     * 使用AlertDialog 启动多选对话框
     * @param view
     */
    public void startCheckDialog(View view){
        //定义单选的选项
        final String[] items = new String[]{
                "AK-47",
                "开山刀",
                "啤酒瓶",
                "板凳",
                "创可贴"
        };
        //使用一个boolean数组表示默认选中项，因为是多选，可以有多个默认选中项，哪项被选中就把值赋为true
        final boolean[] checkedItems = new boolean[]{
                false,
                false,
                false,
                true,
                true
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择：");
        //设置单选内容和点击事件，也可以通过setMultiChoiceItems方法设置多选
        builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(DialogActivity.this, items[which]+"被选中了", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DialogActivity.this, items[which]+"取消选中", Toast.LENGTH_SHORT).show();
                }
                //点击某个选项，就改变该选项对应的boolean值，记录其是否被选中
                checkedItems[which] = isChecked;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogActivity.this, "取消了", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    /**
     * ProgressDialog 进度条对话框
     * @param view
     */
    public void startProgressDialog(View view){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        //TODO yuyang ProgressDialog 就这两种样式
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                int progress = msg.getData().getInt("progress");
                //更新进度
                progressDialog.setProgress(progress);
                return false;
            }
        });
        Thread thread = new Thread(new Runnable() {
            boolean isRunning = true;
            int progress = 0;
            Bundle bundle = new Bundle();
            @Override
            public void run() {
                while (isRunning){
                    progress++;
                    if (progress == 100){
                        isRunning = false;
                    }
                    bundle.putInt("progress", progress);
                    Message message = new Message();
                    message.setData(bundle);
                    handler.sendMessage(message);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    /**
     * DatePickerDialog
     * @param view
     */
    public void startDatePickerDialog(View view){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Toast.makeText(DialogActivity.this, "当前选择时间："+year+":"+monthOfYear, Toast.LENGTH_SHORT).show();
            }
        }, 2016, 5, 20);
        datePickerDialog.show();
    }

    public void startTimePickerDialog(View view){
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(DialogActivity.this, "当前选择时间："+hourOfDay+":"+minute, Toast.LENGTH_SHORT).show();
            }
        }, 15, 15, true);
        timePickerDialog.show();
    }

    /**
     * 使用DialogFragment实现提示 重写onCreateView
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

    /**
     * 启动自定义Dialog
     * @param view
     */
    public void startMyDialog(View view){
        MyDialog dialog = new MyDialog(this);
        dialog.setPromptText("内容区域！！！！内容区域！！！！内容区域！！！！内容区域！！！！内容区域！！！！内容区域！！！！");
        dialog.setCancelable(false);
        dialog.setNegativeListener(new MyDialog.NegativeListener() {
            @Override
            public void negative() {
                Toast.makeText(DialogActivity.this, "取消", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setPositiveListener(new MyDialog.PositiveListener() {
            @Override
            public void positive() {
                Toast.makeText(DialogActivity.this, "确定", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}
