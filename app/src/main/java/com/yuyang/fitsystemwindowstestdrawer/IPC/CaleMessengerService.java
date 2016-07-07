package com.yuyang.fitsystemwindowstestdrawer.IPC;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.yuyang.fitsystemwindowstestdrawer.utils.LogUtils;

/**
 * 使用Messenger实现远程通讯
 * 之所以可以使用Messenger进行远程通讯，是应为android内部自己定义了一个IMessenger.aidl的类，
 * 其实还是使用了AIDL方式。
 * 直接看看Handler和Messenger的源码就知道了
 *
 * ！！那么服务端完全可以做到，使用一个List甚至Map去存储所有绑定的客户端的msg.replyTo对象，然后想给谁发消息都可以。
 *  甚至可以把A进程发来的消息，通过B进程的msg.replyTo发到B进程那里去。
 */
public class CaleMessengerService extends Service {
    private String TAG = "IPC_Messenger_Service";
    private static final int MSG_SUM = 0x110;
    private static final int MSG_MIN = 0x111;

    private Messenger mMessenger = new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msgFromClient) {
            //返回给客户端的消息
            Message msgToClient = Message.obtain(msgFromClient);
            switch (msgFromClient.what){
                case MSG_SUM:
                    try {
                        //TODO 耗时操作，应该起个新线程，这里犯懒了……Y(^_^)Y
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    msgToClient.what = MSG_SUM;
                    try {
                        msgToClient.arg1 = msgFromClient.arg1 + msgFromClient.arg2;
                        msgFromClient.replyTo.send(msgToClient);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case MSG_MIN:
                    msgToClient.what = MSG_MIN;
                    try {
                        msgToClient.arg1 = msgFromClient.arg1 - msgFromClient.arg2;
                        msgFromClient.replyTo.send(msgToClient);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    });

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e(TAG, "onDestroy");
    }
}
