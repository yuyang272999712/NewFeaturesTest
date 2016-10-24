package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.asyncTask.AsyncTaskSimpleActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.childThreadUpdateUI.ChildThreadUpdateUIActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.drawableAbout.DrawableAboutActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.fragmentAbout.FragmentAboutActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.handlerAndMessage.HandlerAndMessageActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.layoutInflate.LayoutInflateActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.layoutInflateFactory.LayoutInflateFactoryActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.loader.LoaderAboutActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.touchEventDispatch.ViewTouchEventActivity;

import java.util.Arrays;
import java.util.List;

/**
 * 源码解析
 */
public class SourceCodeAnalyzeActivity extends AppCompatActivity {
    private List<String> items = Arrays.asList("LayoutInflate方法解析","LayoutInflate setFactory方法","View事件传",
            "drawable相关解析","Handler相关解析","AsyncTask相关解析","Loader相关解析","FragmentManager相关解析",
            "子线程中更新UI");

    private Toolbar toolbar;
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_code);

        setContentView(R.layout.activity_just_list);

        setToolbar();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.item_just_text){
            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null){
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_just_text, parent, false);
                }
                TextView textView = (TextView) convertView.findViewById(R.id.id_info);
                textView.setText(items.get(position));
                return convertView;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position){
                    case 0:
                        intent = new Intent(SourceCodeAnalyzeActivity.this, LayoutInflateActivity.class);
                        break;
                    case 1:
                        intent = new Intent(SourceCodeAnalyzeActivity.this, LayoutInflateFactoryActivity.class);
                        break;
                    case 2:
                        intent = new Intent(SourceCodeAnalyzeActivity.this, ViewTouchEventActivity.class);
                        break;
                    case 3:
                        intent = new Intent(SourceCodeAnalyzeActivity.this, DrawableAboutActivity.class);
                        break;
                    case 4:
                        intent = new Intent(SourceCodeAnalyzeActivity.this, HandlerAndMessageActivity.class);
                        break;
                    case 5:
                        intent = new Intent(SourceCodeAnalyzeActivity.this, AsyncTaskSimpleActivity.class);
                        break;
                    case 6:
                        intent = new Intent(SourceCodeAnalyzeActivity.this, LoaderAboutActivity.class);
                        break;
                    case 7:
                        intent = new Intent(SourceCodeAnalyzeActivity.this, FragmentAboutActivity.class);
                        break;
                    case 8:
                        intent = new Intent(SourceCodeAnalyzeActivity.this, ChildThreadUpdateUIActivity.class);
                        break;

                }
                startActivity(intent);
            }
        });
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("源码解析");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
