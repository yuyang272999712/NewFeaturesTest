package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.loader;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.SimpleCursorAdapter;

import com.yuyang.fitsystemwindowstestdrawer.utils.LogUtils;

/**
    LoaderManager.LoaderCallbacks包含如下三个方法：
     onCreateLoader() 实例化并返回一个新创建给定ID的Loader对象；
            当你尝试使用一个Loader（譬如通过initLoader()方法），它会检查给定Loader的ID是否存在，如果不存在就触发
        LoaderManager.LoaderCallbacks里的onCreateLoader()方法创建一个新Loader。创建新Loader实例典型的做法
        就是通过CursorLoader类创建，不过你也可以自定义一个继承自Loader的子类来实现自己的Loader。
     onLoadFinished() 当创建好的Loader完成了数据的load之后回调此方法；
            当创建好的Loader完成数据加载时回调此方法，我们要确保该方法在Loader释放现有维持的数据之前被调用。在这里
        我们应该移除所有对旧数据的使用（因为旧数据不久就会被释放），但是不用释放旧数据，因为Loader会帮我们完成旧数据的释放。
     onLoaderReset() 当创建好的Loader被reset时调用此方法，这样保证它的数据无效；
            当实例化好的Loader被重启时该方法被回调，这里会让Loader的数据置于无效状态。这个回调方法其实就是为了告诉
        我们啥时候数据要被释放掉，所以我们应该在这个时候移除对原有数据的引用。
 */
public class LoaderContactActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,
                null, new String[]{Phone.DISPLAY_NAME, Phone.NUMBER },
                new int[] { android.R.id.text1, android.R.id.text2 }, 0 );
        setListAdapter(cursorAdapter);

        /**
         * 启动一个Loader（initLoader）
         * 第一个参数代表当前Loader的ID；
         * 第二个参数代表提供给Loader构造函数的参数，可选；
         * 第三个参数代表LoaderManager.LoaderCallbacks的回调实现；
         *  1、如果代表该Loader的ID已经存在，则后面创建的Loader将直接复用已经存在的；
         * TODO yuyang 2、如果代表该Loader的ID不存在，initLoader()会触发LoaderManager.LoaderCallbacks回调
         *      的onCreateLoader()方法创建一个Loader；
         *
         * 重启一个Loader（restartLoader）
         * 参数与启动一样
         */
        getLoaderManager().initLoader(0, null, this);
    }

    //以下为LoaderCallbacks 的回调
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(this, Phone.CONTENT_URI, null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
        LogUtils.e("CursorLoader", "onLoadFinished"+ SystemClock.currentThreadTimeMillis());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}
