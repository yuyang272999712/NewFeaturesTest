package com.yuyang.aidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yuyang.fitsystemwindowstestdrawer.IPC.objectAIDL.Book;
import com.yuyang.fitsystemwindowstestdrawer.IPC.objectAIDL.IBookManager;

import java.util.List;

/**
 * 跨进程传递对象
 */
public class BookAidlActivity extends AppCompatActivity {

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            try {
                List<Book> list = bookManager.getBookList();
                Log.i("BookAidlActivity", "远程获取图书列表成功，"+list.toString());

                Book newBook = new Book(3, "Android开发艺术探索");
                bookManager.addBook(newBook);

                List<Book> newList = bookManager.getBookList();
                Log.i("BookAidlActivity", "从新远程获取图书列表成功，"+newList.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

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
        super.onDestroy();
        unbindService(mConnection);
    }
}
