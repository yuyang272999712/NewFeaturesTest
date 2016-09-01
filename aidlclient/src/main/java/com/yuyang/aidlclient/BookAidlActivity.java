package com.yuyang.aidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yuyang.fitsystemwindowstestdrawer.IPC.objectAIDL.Book;
import com.yuyang.fitsystemwindowstestdrawer.IPC.objectAIDL.IBookManager;
import com.yuyang.fitsystemwindowstestdrawer.IPC.objectAIDL.IOnNewBookArrivedListener;

import java.util.List;

/**
 * 跨进程传递对象
 */
public class BookAidlActivity extends AppCompatActivity {
    private static final String TAG = "BookAidlActivity";
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;

    private IBookManager bookManager;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.d(TAG, "有新书到了："+msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, book).sendToTarget();
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bookManager = IBookManager.Stub.asInterface(service);
            try {
                List<Book> list = bookManager.getBookList();
                Log.i("BookAidlActivity", "远程获取图书列表成功，"+list.toString());

                Book newBook = new Book(3, "Android开发艺术探索");
                bookManager.addBook(newBook);

                List<Book> newList = bookManager.getBookList();
                Log.i("BookAidlActivity", "从新远程获取图书列表成功，"+newList.toString());

                //TODO yuyang 向服务端注册监听
                bookManager.registerListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bookManager = null;
            Log.e(TAG, "binder死亡了");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.yuyang.fitsystemwindowstestdrawer","com.yuyang.fitsystemwindowstestdrawer.IPC.BookService"));
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if (bookManager != null && bookManager.asBinder().isBinderAlive()){
            try {
                Log.i(TAG, "注销监听："+mOnNewBookArrivedListener);
                bookManager.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        super.onDestroy();
    }
}
