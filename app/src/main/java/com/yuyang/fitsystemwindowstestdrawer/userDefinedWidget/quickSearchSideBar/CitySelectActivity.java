package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.quickSearchSideBar;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import com.alibaba.fastjson.JSON;
import com.yuyang.fitsystemwindowstestdrawer.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 字母索引条
 */

public class CitySelectActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ExpandableListView expandableListView;
    private LetterBarView letterBarView;
    private ArrayMap<String, List<City>> datas = new ArrayMap<>();
    private ExpandableAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);

        setToolbar();
        expandableListView = (ExpandableListView) findViewById(R.id.city_select_list);
        letterBarView = (LetterBarView) findViewById(R.id.letter_bar);
        initDatas();

        mAdapter = new ExpandableAdapter(this, datas);

        expandableListView.setAdapter(mAdapter);
        letterBarView.setOnLetterSelectListener(new LetterBarView.OnLetterSelectListener() {
            @Override
            public void onLetterSelect(String s) {
                if (s.equalsIgnoreCase("#")) {
                    expandableListView.setSelection(0);
                } else {
                    if (mAdapter.containsKey(s)) {
                        expandableListView.setSelectedGroup(mAdapter.getKeyPosition(s));
                    }
                }
            }
        });
        for (int i=0; i<datas.size(); i++){
            expandableListView.expandGroup(i);
        }
    }

    private void initDatas() {
        AssetManager assetManager = getAssets();
        try {
            String city_json = getStringFromInputStream(assetManager.open("cities.json"));
            List<City> cities = JSON.parseArray(city_json, City.class);
            for (City city:cities){
                String letterKey = PinyinUtil.getPinyin(city.getName()).substring(0,1);
                if (datas.containsKey(letterKey)){
                    datas.get(letterKey).add(city);
                }else {
                    List<City> cityList = new ArrayList<>();
                    cityList.add(city);
                    datas.put(letterKey, cityList);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("字母索引条");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private static String getStringFromInputStream(InputStream a_is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(a_is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return sb.toString();
    }
}
