package com.yuyang.fitsystemwindowstestdrawer.destWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 2016/5/3.
 */
public class DestStackWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        //迭代每个Widget，创建一个RemoteViews对象，并对每个Widget应用修改后的RemoteViews
        final int N = appWidgetIds.length;
        for (int i = 0; i<N;i++){
            int appWidgetId = appWidgetIds[i];
            //创建一个RemoteView
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.dest_stack_view_widget);
            //将这个Widget绑定到一个RemoteViewService
            Intent intent = new Intent(context, MyRemoteViewService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            views.setRemoteAdapter(appWidgetId, R.id.dest_stack_view, intent);
            //在Widget布局层次中指定一个绑定集合为空时的View
            views.setEmptyView(R.id.dest_stack_view, R.id.dest_stack_view_empty);

            //添加点击事件
            Intent temIntent = new Intent(context,DestWidgetActivity.class);
            temIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, temIntent, 0);
            views.setPendingIntentTemplate(R.id.dest_stack_view, pendingIntent);

            //通知AppWidgetManager使用修改后的远程View更新Widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
