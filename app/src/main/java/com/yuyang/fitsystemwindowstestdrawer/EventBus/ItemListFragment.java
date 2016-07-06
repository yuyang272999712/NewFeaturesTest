package com.yuyang.fitsystemwindowstestdrawer.EventBus;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yuyang.fitsystemwindowstestdrawer.EventBus.EventBeans.ItemListEvent;
import com.yuyang.fitsystemwindowstestdrawer.EventBus.MyEventBus.EventBus;

/**
 * 注册EventBus事件监听
 */
public class ItemListFragment extends ListFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregister(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //开启一个异步任务模拟网络请求
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                EventBus.getInstance().post(new ItemListEvent(Item.ITEMS));
                return null;
            }
        }.execute();
    }

    public void onEventUI(ItemListEvent event){
        setListAdapter(new ArrayAdapter<Item>(getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1, event.getItems()));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        EventBus.getInstance().post(getListView().getItemAtPosition(position));
    }
}
