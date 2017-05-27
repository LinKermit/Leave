package com.android.wang.rxjavalearn.remark;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by wang on 2016/11/24.
 */

public class MyJava extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        Observer<String> observer = new Observer<String>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.d("观察者",s);
//            }
//        };
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d("输出",s);
            }
        };
//
//        Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                //提交的数据
//                subscriber.onNext("1");
//                subscriber.onNext("2");
//                subscriber.onCompleted();
//            }
//        }).subscribe(subscriber);


        Observable.just("Hello", "Hi", "Aloha").subscribe(subscriber);
        String[] names = {"1","2","3","4","5","end"};
        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("from",s);
            }
        });
    }
}
