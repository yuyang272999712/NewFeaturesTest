package com.yuyang.fitsystemwindowstestdrawer.destWidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * RemoteViewsFactory是Adapter类的一个包装器，用于创建和填充将在Collection View Widget中显示的View
 */
public class MyRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<String> myWidgetText = new ArrayList<>();
    private Context context;
    private Intent intent;
    private int widgetId;

    public MyRemoteViewsFactory(Context context, Intent intent){
        this.context = context;
        this.intent = intent;

        widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        //这里的数据获取应该推迟到onDataSetChanged()或getViewAt()中执行
        myWidgetText.add("The1");
        myWidgetText.add("The2");
        myWidgetText.add("The3");
        myWidgetText.add("The4");
        myWidgetText.add("The5");
        myWidgetText.add("The6");
        myWidgetText.add("The7");
        myWidgetText.add("The8");
    }

    @Override
    public void onDataSetChanged() {
        //可以通过AppWidgetManager的notifyAppWidgetViewDataChanged方法来触发

    }

    @Override
    public void onDestroy() {
        myWidgetText.clear();
    }

    @Override
    public int getCount() {
        return myWidgetText.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.layout_dest_stack_view_item);

        rv.setTextViewText(R.id.dest_hour,myWidgetText.get(position));
        rv.setTextViewText(R.id.dest_minute,myWidgetText.get(position));

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(Intent.EXTRA_TEXT, myWidgetText.get(position));
        rv.setOnClickFillInIntent(R.id.dest_hour, fillInIntent);

        return rv;
    }

    //可选，指定一个“加载”View进行显示，返回null时将使用默认的View
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //如果每个项提供的唯一ID是稳定的--即他们不会在运行时改变，返回true
    @Override
    public boolean hasStableIds() {
        return false;
    }
}
