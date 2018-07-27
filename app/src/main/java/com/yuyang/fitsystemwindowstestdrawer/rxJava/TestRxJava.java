package com.yuyang.fitsystemwindowstestdrawer.rxJava;

import com.yuyang.fitsystemwindowstestdrawer.rxJava.learn1.Weather;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
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
                })
                .map(new Function<Weather, Weather>() {
                    @Override
                    public Weather apply(Weather weather) throws Exception {
                        System.out.println("map1线程Thread:"+Thread.currentThread());
                        return weather;
                    }
                })
                .subscribeOn(Schedulers.newThread())//TODO yuyang 让Observable运行在新线程中
                .map(new Function<Weather, Integer>() {
                    @Override
                    public Integer apply(Weather weather) throws Exception {
                        System.out.println("map2线程Thread:"+Thread.currentThread());
                        return 0;
                    }
                })
                .observeOn(Schedulers.io())//TODO yuyang 让subscriber运行在主线程中
                .map(new Function<Integer, Weather>() {
                    @Override
                    public Weather apply(Integer age) throws Exception {
                        System.out.println("map3线程Thread:"+Thread.currentThread());
                        return new Weather();
                    }
                })
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
