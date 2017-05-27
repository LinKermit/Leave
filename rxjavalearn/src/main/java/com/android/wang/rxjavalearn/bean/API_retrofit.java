package com.android.wang.rxjavalearn.bean;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by wang on 2016/11/25.
 */

public interface API_retrofit {

    //1.直接请求型
    @GET("/record")
    Call<ResultData> getResult();

    @GET("/result/{id}")
    Call<ResultData> getResult(@Path("id") String id);

    //2.带参查询型
    //如12306的查询接口https://kyfw.12306.cn/otn/lcxxcx/query?purpose_codes=ADULT&queryDate=2016-03-18&from_station=BJP&to_station=CDW,写法如下:

    @GET("/otn/lcxxcx/query")
    Call<ResultData> query(@Query("purpose_codes") String codes, @Query("queryDate") String date,
                           @Query("from_station") String from, @Query("to_station") String to);


    //3.带Header型
    //比如要更新某个账户信息,其接口地址为/info,需要带的Header有设备信息device,系统版本version,还要带请求参数要更新账户的id,代码如下:

    @POST("/info")
    Call<Object> updateInfo(@Header("device") String device, @Header("version") int version,
                            @Field("id") String id);
}
