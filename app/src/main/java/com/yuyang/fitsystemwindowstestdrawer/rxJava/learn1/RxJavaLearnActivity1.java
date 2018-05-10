package com.yuyang.fitsystemwindowstestdrawer.rxJava.learn1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava使用，请求新浪天气接口
 */
public class RxJavaLearnActivity1 extends AppCompatActivity implements View.OnClickListener {
    private EditText cityET;
    private TextView queryTV;
    private TextView weatherTV;

    private Disposable disposable;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_learn_1);

        //获取控件实例
        cityET = (EditText) findViewById(R.id.city);
        queryTV = (TextView) findViewById(R.id.query);
        weatherTV = (TextView) findViewById(R.id.weather);
        //对查询按钮侦听点击事件
        queryTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.query){
            weatherTV.setText("");
            String city = cityET.getText().toString();
            if(TextUtils.isEmpty(city)){
                Toast.makeText(this, "城市不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            //采用普通写法创建Observable
            observableAsNormal(city);
        }
    }

    /**
     * 创建Observable
     * @param city
     */
    private void observableAsNormal(final String city) {
        Observable.create(new ObservableOnSubscribe<Weather>() {
            @Override
            public void subscribe(ObservableEmitter<Weather> emitter) throws Exception {
                System.out.println("下发线程Thread:"+Thread.currentThread());
                //1.如果已经取消订阅，则直接退出
                if (disposable.isDisposed()){
                    return;
                }
                //2.开网络连接请求获取天气预报，返回结果是xml格式
                String weatherXml = null;
                try {
                    weatherXml = getWeather(city);
                    //3.解析xml格式，返回weather实例
                    Weather weather = Weather.parseWeather(weatherXml);
                    //4.发布事件通知订阅者
                    emitter.onNext(weather);
                    //5.事件通知完成
                    emitter.onComplete();
                } catch (IOException e) {
                    //6.出现异常，通知订阅者
                    emitter.onError(e);
                }
            }
        }).map(new Function<Weather, Weather>() {
            @Override
            public Weather apply(Weather weather) throws Exception {
                System.out.println("map线程Thread:"+Thread.currentThread());
                return weather;
            }
        }).subscribeOn(Schedulers.newThread())//TODO yuyang 让Observable运行在新线程中
        .observeOn(AndroidSchedulers.mainThread())//TODO yuyang 让subscriber运行在主线程中
        .subscribe(new Observer<Weather>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe线程Thread:"+Thread.currentThread());
                disposable = d;
            }

            @Override
            public void onNext(Weather weather) {
                System.out.println("onNext线程Thread:"+Thread.currentThread());
                //对应上面的第4点：subscriber.onNext(weather);
                //这里写获取到某一个事件通知后的处理逻辑
                if(weather != null) {
                    weatherTV.setText(weather.toString());
                }
            }

            @Override
            public void onError(Throwable e) {
                //对应上面的第6点：subscriber.onError(e);
                //这里写出现异常后的处理逻辑
                Toast.makeText(RxJavaLearnActivity1.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                //对应上面的第5点：subscriber.onCompleted();
                //这里写事件发布完成后的处理逻辑
            }
        });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    public String getWeather(String city) throws IOException {
        URL url = new URL("http://php.weather.sina.com.cn/xml.php?city=%B1%B1%BE%A9&password=DJOYnieT8234jlsK&day=0");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        StringBuilder result = new StringBuilder();
        InputStream in = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = null;
        while ((line = reader.readLine()) != null){
            result.append(line);
        }
        connection.disconnect();
        return result.toString();
    }
}
