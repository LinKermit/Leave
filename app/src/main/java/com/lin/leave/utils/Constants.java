package com.lin.leave.utils;

/**
 * Created by Administrator on 2017/4/28.
 */

public class Constants {

    public static final String BASE = "http://192.168.1.111:8080";

    // 请求Json数据基本URL
    public static final String BASE_URL_JSON = BASE+"/atguigu/json/";
    // 请求图片基本URL
    public static final String BASE_URl_IMAGE = BASE+"/atguigu/img";


    //主页Fragment路径
    public static final String HOME_URL = BASE_URL_JSON + "HOME_URL.json";

    //游戏推荐的路径
    public static final String GAME_URL = BASE_URL_JSON + "gamecenter.json";

    //视频路径
    public static final String VIDEO_URL = "http://192.168.1.111:8080/movies/video.json";



    //小裙子
    public static final String SKIRT_URL = BASE_URL_JSON + "SKIRT_URL.json";
    //上衣
    public static final String JACKET_URL = BASE_URL_JSON + "JACKET_URL.json";
    //下装(裤子)
    public static final String PANTS_URL = BASE_URL_JSON + "PANTS_URL.json";
    //外套
    public static final String OVERCOAT_URL = BASE_URL_JSON + "OVERCOAT_URL.json";
    //配件
    public static final String ACCESSORY_URL = BASE_URL_JSON + "ACCESSORY_URL.json";
    //包包
    public static final String BAG_URL = BASE_URL_JSON + "BAG_URL.json";
    //装扮
    public static final String DRESS_UP_URL = BASE_URL_JSON + "DRESS_UP_URL.json";
    //居家宅品
    public static final String HOME_PRODUCTS_URL = BASE_URL_JSON + "HOME_PRODUCTS_URL.json";
    //办公文具
    public static final String STATIONERY_URL = BASE_URL_JSON + "STATIONERY_URL.json";
    //数码周边
    public static final String DIGIT_URL = BASE_URL_JSON +  "DIGIT_URL.json";
    //游戏专区
    public static final String GAME2_URL = BASE_URL_JSON + "GAME_URL.json";
}
