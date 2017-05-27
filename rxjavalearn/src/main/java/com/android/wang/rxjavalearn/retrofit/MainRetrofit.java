package com.android.wang.rxjavalearn.retrofit;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.android.wang.rxjavalearn.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wang on 2016/11/24.
 */

public class MainRetrofit extends Activity{

    private RecyclerView mRecyclerView;
 //   private List<GankBean.GankResult> imageDetailList = new ArrayList<GankBean.GankResult>();
    private ImageAdapter mAdapter;
    SwipeRefreshLayout refreshLayout;


    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();


    private static final String BASE_URL = "http://gank.io/api/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        initView();
        querybyObser();
    }

    private void initView()
    {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_rxjava);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout_rxjava);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }
/*
    private void query()
    {
        //创建retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        //2.创建访问API的请求
        GankApi gankApi = retrofit.create(GankApi.class);
        Call<GankBean> call = gankApi.getBeauties(10,1);
        //3.发送请求

        call.enqueue(new Callback<GankBean>() {
            @Override
            public void onResponse(Call<GankBean> call, Response<GankBean> response) {
                GankBean gankBean = response.body();
                List<GankBean.GankResult> gankResults= gankBean.getResults();
                Log.d("请求成功",gankResults.get(0).getUrl());
            }

            @Override
            public void onFailure(Call<GankBean> call, Throwable t) {

            }
        });
    }
*/
    private void querybyObser()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
        GankApi gankApi = retrofit.create(GankApi.class);

        gankApi.getObservable(10,1)
                .doOnNext(new Action1<GankBean>() {
                    @Override
                    public void call(GankBean gankBean) {
                        //后台线程处理耗时操作
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Rxjava请求失败",e.toString());
                    }

                    @Override
                    public void onNext(GankBean gankBean) {
                        List<GankBean.GankResult> gankResults= gankBean.getResults();

                        mRecyclerView.setAdapter(mAdapter = new ImageAdapter(gankResults,MainRetrofit.this));
                        Log.d("Rxjava请求成功",gankResults.get(0).getUrl());
                    }
                });
    }
}
