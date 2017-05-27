package com.lin.leave.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.lin.leave.R;
import com.lin.leave.adapter.home.VideoAdapter;
import com.lin.leave.base.BaseFragment;
import com.lin.leave.bean.VideoBean;
import com.lin.leave.utils.Constants;
import com.lin.leave.utils.FileCacheUtils;
import com.lin.leave.view.CustomEmptyView;
import com.lin.leave.view.DividerItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Lin on 2017/5/5.
 */

public class VideoFragment extends BaseFragment {
    private static final String TAG = "VideoFragment";
    private RecyclerView recyclerView;
    private CustomEmptyView emptyView;
    private VideoAdapter adapter;
    private VideoBean videoBean;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.fragment_video,null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        emptyView = (CustomEmptyView) view.findViewById(R.id.empty_view);
        adapter = new VideoAdapter(mContext,null);
        LinearLayoutManager manager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL_LIST));
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

    String url = Constants.VIDEO_URL;
    public void getHomeDataFromNet()
    {
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new VideoFragment.MyStringCallback());
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

    private void processJson(String response) {
        Gson gson = new Gson();
        videoBean = gson.fromJson(response,VideoBean.class);

        emptyView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        adapter = new VideoAdapter(mContext,videoBean);
        recyclerView.setAdapter(adapter);
    }


    /**
     * 数据加载失败
     */
    private void showEmptyView(){
        emptyView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        emptyView.setmEmptyImg(R.drawable.img_tips_error_load_error);
        emptyView.setmEmptyText("加载失败~(≧▽≦)~啦啦啦.");
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
