package com.yuyang.fitsystemwindowstestdrawer.internetAbout;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.internetAbout.httpAbout.HttpURLConnectionActivity;
import com.yuyang.fitsystemwindowstestdrawer.internetAbout.manager.ConnectivityStateActivity;
import com.yuyang.fitsystemwindowstestdrawer.internetAbout.retrofitAbout.RetrofitReqUserInfoActivity;

import java.util.Arrays;
import java.util.List;

/**
 * 网络相关.
 */
public class InternetAboutActivity extends ListActivity {
    private List<String> items = Arrays.asList("获取网络状态",
            "使用HttpURLConnection",
            "OKHttp",
            "Retrofit");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.item_just_text, R.id.id_info, items));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = null;
        switch (position){
            case 0:
                intent = new Intent(this, ConnectivityStateActivity.class);
                break;
            case 1:
                intent = new Intent(this, HttpURLConnectionActivity.class);
                break;
            case 2:
            case 3:
                intent = new Intent(this, RetrofitReqUserInfoActivity.class);
                break;
        }
        startActivity(intent);
    }
}
