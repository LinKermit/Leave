package com.android.wang.rxjavalearn.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by wang on 2016/11/24.
 */

public interface GankApi {
    @GET("data/福利/{number}/{page}")
 //   Call<GankBean> getBeauties(@Path("number") int number, @Path("page") int page);

    Observable<GankBean> getObservable(@Path("number") int number, @Path("page") int page);
}
