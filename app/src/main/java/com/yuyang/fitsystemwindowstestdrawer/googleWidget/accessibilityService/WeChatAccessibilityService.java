package com.yuyang.fitsystemwindowstestdrawer.googleWidget.accessibilityService;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.PendingIntent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * AccessibilityService就是一个后台监控服务，设计用来帮助使用障碍的人士
 *
 * 在手机无障碍设置中开启
 * 微信6.5.4
 */

public class WeChatAccessibilityService extends AccessibilityService {
    private static final String TAG = "MyAccessibilityService";
    private List<AccessibilityNodeInfo> newNodes;//新出现的红包
    private boolean inProgress = false;//是否在开红包的过程中
    private boolean comeFromNotification = false;//界面更新来自于通知
    /**
     * 当启动服务的时候被调用
     */
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        newNodes = new ArrayList<>();
        Log.e(TAG, "抢红包服务已启动！");
    }

    /**
     * 窗口事件回调
     * @param event
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e(TAG, "进入了事件处理");
        int eventType = event.getEventType();
        switch (eventType){
            //当通知栏发生改变时
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                Log.e(TAG, "通知栏发生变化-可能有微信红包的通知");
                List<CharSequence> texts = event.getText();
                if (!texts.isEmpty()) {
                    for (CharSequence text : texts) {
                        String content = text.toString();
                        if (content.contains("[微信红包]")) {
                            //模拟打开通知栏消息，即打开微信
                            if (event.getParcelableData() != null &&
                                    event.getParcelableData() instanceof Notification) {
                                Notification notification = (Notification) event.getParcelableData();
                                PendingIntent pendingIntent = notification.contentIntent;
                                try {
                                    comeFromNotification = true;
                                    pendingIntent.send();
                                    Log.e(TAG, "进入微信");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                Log.e(TAG, "TYPE_WINDOW_CONTENT_CHANGED："+event.getClassName().toString());
                if (comeFromNotification) {
                    comeFromNotification = false;
                    getLastPacket();
                }
                break;
            //当窗口的状态发生改变时
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                String className = event.getClassName().toString();
                Log.e(TAG, "TYPE_WINDOW_STATE_CHANGED："+className);
                if (className.equals("com.tencent.mm.ui.LauncherUI")) {
                    //点击最后一个红包
                    Log.e(TAG,"获取红包节点");
                    getLastPacket();
                } else if (className.equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI")) {
                    //开红包
                    Log.e(TAG,"开红包");
                    inputClick(0);
                } else if (className.equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI")) {
                    //退出红包
                    Log.e(TAG,"退出红包");
                    inputClick(1);
                }
                break;
            case AccessibilityEvent.TYPE_WINDOWS_CHANGED:
                Log.e(TAG, "TYPE_WINDOWS_CHANGED："+"页面改变了");
                break;
        }
    }

    /**
     * 通过ID获取控件，并进行模拟点击
     * @param actionType
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void inputClick(int actionType) {
        inProgress = true;
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list;//节点集合
            switch (actionType){
                case 0://开红包
                    list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bi3");
                    for (AccessibilityNodeInfo item : list) {
                        item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                    break;
                case 1://退出红包
                    //微信6.3.31
                    list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/gv");
                    for (AccessibilityNodeInfo item : list) {
                        item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                    break;
            }
        }
    }

    /**
     * 获取List中最后一个红包，并进行模拟点击
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void getLastPacket() {
        if (inProgress){//正在抢红包
            inProgress = false;
            return;
        }
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            //id/a4w是红包的item
            List<AccessibilityNodeInfo> list = rootNode.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/a4w");
            if (list != null && list.size() > 0) {//如果是在聊天页面，那肯定能找到唯一的一个node
                AccessibilityNodeInfo itemNode = list.get(list.size() - 1);
                itemNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                inProgress = true;
                /*recycle(list.get(0));
                if (newNodes.size() > 0) {
                    Log.e(TAG, "点击聊天列表上的红包item");
                    AccessibilityNodeInfo itemNode = newNodes.get(newNodes.size() - 1);
                    itemNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    inProgress = true;
                    newNodes.clear();
                }*/
            }
        }
    }

    /**
     * 回归函数遍历每一个节点，并将含有"领取红包"存进List中
     *
     * @param info
     */
    public void recycle(AccessibilityNodeInfo info) {
        if (info.getChildCount() == 0) {
            if (info.getText() != null) {
                if ("领取红包".equals(info.getText().toString())) {
                    /*if (info.isClickable()) {
                        info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }*/
                    AccessibilityNodeInfo parent = info.getParent();
                    while (parent != null) {
                        if (parent.isClickable()){
                            newNodes.add(parent);
                            break;
                        }
                        parent = parent.getParent();
                    }
                }
            }
        } else {
            for (int i = 0; i < info.getChildCount(); i++) {
                if (info.getChild(i) != null) {
                    recycle(info.getChild(i));
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
