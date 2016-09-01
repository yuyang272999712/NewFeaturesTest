package com.yuyang.fitsystemwindowstestdrawer.IPC;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yuyang.fitsystemwindowstestdrawer.IPC.objectAIDL.Book;
import com.yuyang.fitsystemwindowstestdrawer.IPC.objectAIDL.IBookManager;
import com.yuyang.fitsystemwindowstestdrawer.IPC.objectAIDL.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 跨进程传递对象
 */
public class BookService extends Service {
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList =
            new RemoteCallbackList<>();
    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);

    private IBookManager.Stub mBinder = new IBookManager.Stub() {
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            /*
            //权限检测
            int check = checkCallingOrSelfPermission("com.yuyang.fitsystemwindowstestdrawer");
            if (check == PackageManager.PERMISSION_DENIED){
                return false;
            }
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length>0){
                packageName = packages[0];
            }
            if (!packageName.startsWith("com.yuyang")){
                return false;
            }*/
            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.register(listener);
            Log.d("BookService", "注册成功");
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.unregister(listener);
            Log.d("BookService", "listener注销成功");
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //调用权限限制
        //checkCallingOrSelfPermission()
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "Ios"));
        new Thread(new ServiceWorker()).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestroyed.set(true);
    }

    /**
     * 每隔3秒增加一个Book，并通知listener
     */
    private class ServiceWorker implements Runnable {
        @Override
        public void run() {
            while (!mIsServiceDestroyed.get()){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size()+1;
                Book newBook = new Book(bookId, "new Book#"+bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 通知所有监听者有新书到了
     * @param book
     * @throws RemoteException
     */
    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
        int N = mListenerList.beginBroadcast();
        for (int i=0; i<N; i++){
            IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
            if (listener != null){
                listener.onNewBookArrived(book);
                Log.d("BookService", "通知Listener："+listener);
            }
        }
        mListenerList.finishBroadcast();
    }
}
