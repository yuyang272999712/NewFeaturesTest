package com.yuyang.fitsystemwindowstestdrawer.rxJava;

import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.rxJava.learn1.RxJavaLearnActivity1;
import com.yuyang.fitsystemwindowstestdrawer.rxJava.learn1.Weather;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yuyang on 2018/5/9.
 */

public class TestRxJava {
    /*public static Observable getRxSchedulerHelper{
        return Observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread());
    }*/

    public static void main(String[] args){
//        Observable.just("测试1", "测试2")
//                .compose()
        Observable.create(new ObservableOnSubscribe<Weather>() {
            @Override
            public void subscribe(ObservableEmitter<Weather> emitter) throws Exception {
                System.out.println("下发线程Thread:"+Thread.currentThread());

                Weather weather = new Weather();
                //4.发布事件通知订阅者
                emitter.onNext(weather);
                //5.事件通知完成
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())//TODO yuyang 让Observable运行在新线程中
                //.observeOn(Schedulers.newThread())//TODO yuyang 让subscriber运行在主线程中
                .subscribe(new Observer<Weather>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("接收线程Thread onSubscribe:"+Thread.currentThread());
                    }

                    @Override
                    public void onNext(Weather weather) {
                        System.out.println("接收线程Thread onNext:"+Thread.currentThread());
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("接收线程Thread onError:"+Thread.currentThread());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("接收线程Thread onComplete:"+Thread.currentThread());
                    }
                });
    }
}
