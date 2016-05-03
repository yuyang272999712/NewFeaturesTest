package com.yuyang.fitsystemwindowstestdrawer.destWidget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * RemoteViewsService是实例化和管理RemoteViewsFactory的包装类
 *
 * RemoteViewsFactory用来提供Collection View Widget中显示的每个View
 */
public class MyRemoteViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MyRemoteViewsFactory(getApplicationContext(), intent);
    }
}
