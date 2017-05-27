package com.lin.leave.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lin.leave.R;
import com.lin.leave.adapter.home.HomeAdapter;
import com.lin.leave.base.BaseFragment;
import com.lin.leave.bean.HomeBean;
import com.lin.leave.utils.Constants;
import com.lin.leave.utils.FileCacheUtils;
import com.lin.leave.view.CustomEmptyView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Lin on 2017/5/5.
 */

public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";

    CustomEmptyView customEmptyView;//数据加载失败
    private RecyclerView recyclerView;
    private HomeAdapter adapter;
    private HomeBean.ResultBean resultBean;
    GridLayoutManager manager;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.fragment_home,null);
        customEmptyView = (CustomEmptyView) view.findViewById(R.id.empty_view);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_home);
        adapter = new HomeAdapter(mContext,null);
        manager = new GridLayoutManager(mContext,1);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    protected void initData() {
        String cache = FileCacheUtils.getString(mContext,url);
        if (!TextUtils.isEmpty(cache)){
            processJson(cache);
        }else {
            getHomeDataFromNet();
        }

    }
    String url = Constants.HOME_URL;
    public void getHomeDataFromNet()
    {

        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }


    public class MyStringCallback extends StringCallback
    {
        @Override
        public void onBefore(Request request, int id)
        {

        }

        @Override
        public void onAfter(int id)
        {
            Log.e(TAG, "onAfter: " );
        }

        @Override
        public void onError(Call call, Exception e, int id)
        {
            e.printStackTrace();
        }

        @Override
        public void onResponse(String response, int id)
        {
            Log.e(TAG, "onResponse：complete");
            if (response!=null){
                FileCacheUtils.putString(mContext,url,response);
                processJson(response);
//                manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                    @Override
//                    public int getSpanSize(int position) {
//                        return 1;
//                    }
//                });
            }else {
                showEmptyView();
            }
        }

        @Override
        public void inProgress(float progress, long total, int id)
        {
            Log.e(TAG, "inProgress:" + progress);
        }
    }

    /**
     * 解析数据
     * @param response
     */
    private void processJson(String response) {
        JsonObject object = new JsonParser().parse(response).getAsJsonObject();
        JsonObject jsonElement = object.getAsJsonObject("result");
        Gson gson = new Gson();
        resultBean = gson.fromJson(jsonElement,HomeBean.ResultBean.class);

        customEmptyView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        adapter = new HomeAdapter(mContext,resultBean);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 数据加载失败
     */
    private void showEmptyView(){
        customEmptyView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        customEmptyView.setmEmptyImg(R.drawable.img_tips_error_load_error);
        customEmptyView.setmEmptyText("加载失败~(≧▽≦)~啦啦啦.");
    }

    @Override
    public void onResume() {//解决滑动不显示数据
        super.onResume();
        initData();
    }
}
