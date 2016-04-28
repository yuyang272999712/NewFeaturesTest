package com.yuyang.fitsystemwindowstestdrawer.destWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.Date;

/**
 * Created by yuyang on 2016/4/28.
 */
public class DestWidget extends AppWidgetProvider {
    public static final String TIME_CHANGE = "com.yuyang.time_change.REFRESHED";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        //为桌面小工具添加点击事件
        Intent intent = new Intent(context,DestWidgetActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews views1 = new RemoteViews(context.getPackageName(), R.layout.dest_widget);
        views1.setOnClickPendingIntent(R.id.dest_hour, pendingIntent);
        views1.setOnClickPendingIntent(R.id.dest_minute, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, views1);

        Date date = new Date();
        int hour = date.getHours();
        int minute = date.getMinutes();

        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++){
            int appWidgetId = appWidgetIds[i];
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.dest_widget);
            views.setTextViewText(R.id.dest_hour, hour+"");
            views.setTextViewText(R.id.dest_minute, minute+"");
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(TIME_CHANGE.equals(intent.getAction())){
            updateWidget(context);
        }
    }

    private void updateWidget(Context context) {
        ComponentName thisWidget = new ComponentName(context, DestWidget.class);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        onUpdate(context,appWidgetManager,appWidgetIds);
    }
}
