package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.loader;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.loader.MyLoader.AppEntry;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.loader.MyLoader.AppListLoader;

import java.util.List;

/**
 * 谷歌官方demo
 * 加载本地应用
 */
public class AppListActivity extends ListActivity implements LoaderManager.LoaderCallbacks<List<AppEntry>> {
    AppListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new AppListAdapter(this);
        setListAdapter(mAdapter);
        //启动Loader
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Insert desired behavior here.
        Log.i("LoaderCustom", "Item clicked: " + id);
    }

    @Override
    public Loader<List<AppEntry>> onCreateLoader(int id, Bundle args) {
        return new AppListLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<AppEntry>> loader, List<AppEntry> data) {
        mAdapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<AppEntry>> loader) {
        mAdapter.setData(null);
    }

}
