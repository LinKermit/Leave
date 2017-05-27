package com.android.wang.rxjavalearn;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by wang on 2016/11/23.
 */

public class SchedulerActivity extends Activity {
    public final static String tag = "SCHEDULER";
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);

       //Scheduler,subscribeOn/observeOn
        Observable.just(1,2,3,4)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程.subscribeOn() 的位置放在哪里都可以，但它是只能调用一次的

                //observeOn() 指定的是它之后的操作所在的线程
//                .observeOn(Schedulers.newThread())
//                .map(mapOperator) // 新线程，由 observeOn() 指定
//                .observeOn(Schedulers.io())
//                .map(mapOperator2) // IO 线程，由 observeOn() 指定

                .observeOn(AndroidSchedulers.mainThread())// 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer number) {
                Log.d(tag, "number:" + number);
            }
        });

        imageView = (ImageView) findViewById(R.id.imageView2);
        Observable.create(new Observable.OnSubscribe<Drawable>(){
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {

                Drawable drawable = ContextCompat.getDrawable(SchedulerActivity.this,R.mipmap.ic_launcher);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Drawable>() {
                    @Override
                    public void call(Drawable drawable) {
                        imageView.setImageDrawable(drawable);
                    }
                });
//            .subscribe(new Observer<Drawable>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Toast.makeText(SchedulerActivity.this, "Error!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNext(Drawable drawable) {
//                imageView.setImageDrawable(drawable);
//            }
//        });



//        //Func1 包装的是有返回值的方法
//        Observable.just("image/logo.png")
//                .map(new Func1<String, Bitmap>() {
//                    @Override
//                    public Bitmap call(String filePath) {
//                        return getBitmapFromPath(filePath);
//                    }
//                }).subscribe(new Action1<Bitmap>() {
//            @Override
//            public void call(Bitmap bitmap) {
//                showBitmap(bitmap);
//            }
//        });
//
//
//    }
//
//    public Bitmap getBitmapFromPath(String filePath)
//    {
//        return null;
//    }
//
//    public void showBitmap(Bitmap bitmap){
//
    }
}
