package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.touchEventDispatch.eventConflict_2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.touchEventDispatch.eventConflict_2.ui.HorizontalScrollViewEx2;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.touchEventDispatch.eventConflict_2.ui.ListViewEx2;
import com.yuyang.fitsystemwindowstestdrawer.utils.ScreenUtils;

import java.util.ArrayList;

import static com.yuyang.fitsystemwindowstestdrawer.R.id.list;

/**
 * 内部拦截法解决事件冲突
 */
public class EventConflictActivity2 extends AppCompatActivity {
    private static final String TAG = "EventConflictActivity2";
    private HorizontalScrollViewEx2 mContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_conflict_2);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mContainer = (HorizontalScrollViewEx2) findViewById(R.id.container);
        int screenWidth = ScreenUtils.getScreenWidth(this);
        int screenHeight = ScreenUtils.getStatusHeight(this);
        for (int i=0; i<3; i++){
            ViewGroup contentLayout = (ViewGroup) inflater.inflate(R.layout.layout_event_conflict_content_2, mContainer, false);
            contentLayout.getLayoutParams().width = screenWidth;
            TextView textView = (TextView) contentLayout.findViewById(R.id.title);
            textView.setText("page "+ (i+1));
            contentLayout.setBackgroundColor(Color.rgb(255/(i+1), 255/(i+1), 0));
            createList(contentLayout);
            mContainer.addView(contentLayout);
        }
    }

    private void createList(ViewGroup contentLayout) {
        ListViewEx2 listView = (ListViewEx2) contentLayout.findViewById(list);
        ArrayList<String> datas = new ArrayList<>();
        for (int i=0; i<50; i++){
            datas.add("name "+i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_just_text, R.id.id_info, datas);
        listView.setAdapter(adapter);
        listView.setHorizontalScrollViewEx2(mContainer);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent action:" + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent action:" + event.getAction());
        return super.onTouchEvent(event);
    }
}
