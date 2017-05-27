package com.android.wang.rxjavalearn;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    //同步的观察者模式
    //创建出 Observable 和 Subscriber ，再用 subscribe() 将它们串起来
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        //观察者的两种方式
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onNext(String s) {
                Log.d("tag", "Item: " + s);
            }

            @Override
            public void onCompleted() {
                Log.d("tag", "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("tag", "Error!");
            }
        };

        Subscriber<String> subscriber = new Subscriber<String>() {//Observer 的抽象类
            @Override
            public void onNext(String s) {
                Log.d("tag_subscriber", "Item: " + s);
            }

            @Override
            public void onCompleted() {
                Log.d("tag_subscriber", "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("tag_subscriber", "Error!");
            }
        };


        //事件提交的三种方法
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                //这是要提交的数据
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });
        //2、Observable<String> observable2 = Observable.just("Hello", "Hi", "Aloha");

        //3、String[] words = {"Hello", "Hi", "Aloha"};
        // Observable<String> observable3 = Observable.from(words);


        //被观察者通过subscribe提交给观察者
        observable.subscribe(observer);//或observable.subscribe(subscriber);
        */

        //a.打印字符串
        String[] names = {"1","2","3","4","5","end"};
        Observable.from(names)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                    Log.d("打印字符串",s);
                    }
                });

        //b.由 id 取得图片并显示
        imageView = (ImageView)findViewById(R.id.imageView);
        Observable.create(new Observable.OnSubscribe<Drawable>(){
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {

                Drawable drawable = ContextCompat.getDrawable(MainActivity.this,R.mipmap.ic_launcher);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Drawable>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        imageView.setImageDrawable(drawable);
                    }
                });
    }
}
