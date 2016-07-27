package com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteTestAndJsoup;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteTestAndJsoup.DBAbout.AddressDBUtils;
import com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteTestAndJsoup.bean.AddressBaseBean;
import com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteTestAndJsoup.bean.City;
import com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteTestAndJsoup.bean.County;
import com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteTestAndJsoup.bean.Province;
import com.yuyang.fitsystemwindowstestdrawer.jsoup.ExtractResultCallBack;
import com.yuyang.fitsystemwindowstestdrawer.jsoup.ExtractService;
import com.yuyang.fitsystemwindowstestdrawer.jsoup.Rule;
import com.yuyang.fitsystemwindowstestdrawer.utils.ToastUtils;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * 使用Jsoup抓取网页数据获取全国省市县的分级并保存至本地数据库
 *
 * TODO 逻辑与显示未分离，写得不好
 */
public class AddressSelectActivity extends AppCompatActivity {
    private static final int PROVINCE_LEVEL = 0;
    private static final int CITY_LEVEL = 1;
    private static final int COUNTY_LEVEL = 2;

    private Toolbar toolbar;
    private TextView address;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private AddressAdapter addressAdapter;
    private ProgressDialog progressDialog;

    private List<Province> provinces;
    private List<City> cities;
    private List<County> counties;
    private Province currentProvince;
    private City currentCity;
    private County currentCounty;
    private int CURRENT_LEVEL = PROVINCE_LEVEL;
    private AddressAdapter.OnItemClickCallBack callBack = new AddressAdapter.OnItemClickCallBack() {
        @Override
        public void onItemClickCallBack(AddressBaseBean address) {
            if (CURRENT_LEVEL == PROVINCE_LEVEL){
                currentProvince = (Province) address;
                getCities(currentProvince.getSpell());
                updateAddress(CURRENT_LEVEL);
            }else if (CURRENT_LEVEL == CITY_LEVEL){
                currentCity = (City) address;
                getCounties(currentCity.getSpell());
                updateAddress(CURRENT_LEVEL);
            }else {
                currentCounty = (County) address;
                updateAddress(CURRENT_LEVEL);
            }
        }
    };

    private HandlerThread handlerThread;
    private Handler threadHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_select);
        findViews();
        initDatas();
    }

    private void initDatas() {
        handlerThread = new HandlerThread("jsoup");
        handlerThread.start();
        threadHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.show();
                    }
                });
                super.handleMessage(msg);
                if (msg.what == PROVINCE_LEVEL) {
                    Rule rule = new Rule("http://www.meet99.com/map", null, null, "selected", Rule.CLASS, Rule.GET);
                    ExtractService.extract(rule, new ExtractResultCallBack() {
                        @Override
                        public void onSuccess(Elements results) {
                            provinces.clear();
                            for (Element element : results) {
                                Elements lis = element.parent().getElementsByClass("hasChildren");
                                for (Element li:lis) {
                                    String spell = li.attr("id");
                                    Elements as = li.getElementsByTag("a");
                                    for (Element a:as){
                                        String name = a.text();
                                        provinces.add(new Province(0, spell, name));
                                    }
                                }
                            }
                            AddressDBUtils.saveProvinces(provinces);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    addressAdapter.setAddresses(provinces);
                                }
                            });
                        }

                        @Override
                        public void onError(String errorMsg) {
                            ToastUtils.showLong(getApplicationContext(), errorMsg);
                        }
                    });
                }else if (msg.what == CITY_LEVEL){
                    final String province_spell = (String) msg.obj;
                    Rule rule = new Rule("http://www.meet99.com/map-"+ province_spell +".html", null, null, "selected", Rule.CLASS, Rule.GET);
                    ExtractService.extract(rule, new ExtractResultCallBack() {
                        @Override
                        public void onSuccess(Elements results) {
                            cities.clear();
                            for (Element element : results) {
                                Elements lis = element.parent().getElementsByClass("hasChildren");
                                for (Element li:lis) {
                                    String spell = li.attr("id");
                                    Elements as = li.getElementsByTag("a");
                                    for (Element a:as){
                                        String name = a.text();
                                        cities.add(new City(0, province_spell, spell, name));
                                    }
                                }
                            }
                            AddressDBUtils.saveCities(cities, province_spell);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    addressAdapter.setAddresses(cities);
                                }
                            });
                            CURRENT_LEVEL = CITY_LEVEL;
                        }

                        @Override
                        public void onError(String errorMsg) {
                            ToastUtils.showLong(getApplicationContext(), errorMsg);
                        }
                    });
                }else if (msg.what == COUNTY_LEVEL){
                    final String city_spell = (String) msg.obj;
                    Rule rule = new Rule("http://www.meet99.com/map-"+ city_spell +".html", null, null, "selected", Rule.CLASS, Rule.GET);
                    ExtractService.extract(rule, new ExtractResultCallBack() {
                        @Override
                        public void onSuccess(Elements results) {
                            counties.clear();
                            for (Element element : results) {
                                Elements as = element.parent().getElementsByTag("a");
                                for (Element a:as){
                                    String name = a.text();
                                    counties.add(new County(0, city_spell, null, name));
                                }
                            }
                            AddressDBUtils.saveCounties(counties, city_spell);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    addressAdapter.setAddresses(counties);
                                }
                            });
                            CURRENT_LEVEL = COUNTY_LEVEL;
                        }

                        @Override
                        public void onError(String errorMsg) {
                            ToastUtils.showLong(getApplicationContext(), errorMsg);
                        }
                    });
                }
            }
        };

        linearLayoutManager = new LinearLayoutManager(this);
        addressAdapter = new AddressAdapter(null, callBack);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(addressAdapter);

        getProvinces();
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        address = (TextView) findViewById(R.id.address);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("正在从网络抓取数据...");
        progressDialog.setIndeterminate(true);

        toolbar.setTitle("利用Jsoup获取网络数据并保存之本地数据库");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getProvinces(){
        provinces = AddressDBUtils.getProvince();
        if (provinces == null || provinces.isEmpty()){
            Message message = threadHandler.obtainMessage();
            message.what = PROVINCE_LEVEL;
            threadHandler.sendMessage(message);
        }else {
            addressAdapter.setAddresses(provinces);
            CURRENT_LEVEL = PROVINCE_LEVEL;
        }
    }

    private void getCities(String province_spell){
        cities = AddressDBUtils.getCityByProvince(province_spell);
        if (cities == null || cities.isEmpty()){
            Message message = threadHandler.obtainMessage();
            message.what = CITY_LEVEL;
            message.obj = province_spell;
            threadHandler.sendMessage(message);
        }else {
            addressAdapter.setAddresses(cities);
            CURRENT_LEVEL = CITY_LEVEL;
        }
    }

    private void getCounties(String city_spell){
        counties = AddressDBUtils.getCountyByCity(city_spell);
        if (counties == null || counties.isEmpty()){
            Message message = threadHandler.obtainMessage();
            message.what = COUNTY_LEVEL;
            message.obj = city_spell;
            threadHandler.sendMessage(message);
        }else {
            addressAdapter.setAddresses(counties);
            CURRENT_LEVEL = COUNTY_LEVEL;
        }
    }

    /**
     * 更新最上方显示的地址
     * @param current_level
     */
    private void updateAddress(int current_level) {
        if (current_level == PROVINCE_LEVEL){
            currentCity = null;
            currentCounty = null;
        }else if (current_level == CITY_LEVEL){
            currentCounty = null;
        }
        StringBuilder builder = new StringBuilder();
        if (currentProvince != null){
            builder.append(currentProvince.getName()+",");
        }
        if (currentCity != null){
            builder.append(currentCity.getName()+",");
        }
        if (currentCounty != null){
            builder.append(currentCounty.getName());
        }
        address.setText(builder.toString());
    }

    @Override
    protected void onStop() {
        super.onStop();
        handlerThread.quit();
    }

    @Override
    public void onBackPressed() {
        if (CURRENT_LEVEL == COUNTY_LEVEL){
            getCities(currentProvince.getSpell());
        }else if (CURRENT_LEVEL == CITY_LEVEL){
            getProvinces();
        }else {
            super.onBackPressed();
        }
    }
}
