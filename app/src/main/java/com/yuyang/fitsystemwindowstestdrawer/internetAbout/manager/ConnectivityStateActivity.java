package com.yuyang.fitsystemwindowstestdrawer.internetAbout.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 获取当前网络状态
 */
public class ConnectivityStateActivity extends AppCompatActivity {
    private TextView state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectivity_manager);

        state = (TextView) findViewById(R.id.connectivity_state);

        String service = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(service);

        //获取网络活动信息
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
        boolean isWiFi = activeNetwork!=null && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
        state.setText("是否有网络："+isConnected+";\n"+
                "getExtraInfo："+activeNetwork.getExtraInfo()+";\n"+
                "getReason："+activeNetwork.getReason()+";\n"+
                "getType："+activeNetwork.getType()+";\n"+
                "getTypeName："+activeNetwork.getTypeName()+";\n"+
                "getSubtype："+activeNetwork.getSubtype()+";\n"+
                "getSubtypeName："+activeNetwork.getSubtypeName()+";\n"+
                "getState："+activeNetwork.getState());
    }
}
