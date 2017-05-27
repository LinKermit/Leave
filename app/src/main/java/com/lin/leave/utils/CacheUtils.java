package com.lin.leave.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/5/3.
 */

public class CacheUtils {


    public static final String NIGHT = "night";

    public static boolean getNight(Context mContext, String key) {
        SharedPreferences sp = mContext.getSharedPreferences(NIGHT,Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }

    public static void putNight(Context mContext, String key, boolean value) {
        SharedPreferences sp = mContext.getSharedPreferences(NIGHT, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }
}
