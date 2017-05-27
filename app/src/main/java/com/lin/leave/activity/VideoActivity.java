package com.lin.leave.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lin.leave.R;
import com.lin.leave.adapter.VideoAdapter;
import com.lin.leave.adapter.home.HomeAdapter;
import com.lin.leave.base.BaseActivity;
import com.lin.leave.bean.HomeBean;
import com.lin.leave.fragment.HomeFragment;
import com.lin.leave.utils.Constants;
import com.lin.leave.view.DividerItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;
import okhttp3.Request;

public class VideoActivity extends BaseActivity {
    private static final String TAG = "VideoActivity";
    private JCVideoPlayerStandard jcVideoPlayer;
    private RecyclerView recyclerView;
    private HomeBean.ResultBean resultBean;
    private VideoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initView();
        initData();
    }

    private void initData() {
        getHomeDataFromNet();
    }

    private void initView() {
        jcVideoPlayer = (JCVideoPlayerStandard)findViewById(R.id.jc_video);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_video);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));

        jcVideoPlayer.setUp("http://192.168.1.111:8080/The1.mp4", JCVideoPlayer.SCREEN_LAYOUT_LIST,
                "这是我的视频");
        Glide.with(jcVideoPlayer.getContext())
                .load("http://192.168.1.111:8080/tomcat.png")
                .into(jcVideoPlayer.thumbImageView);
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    public void getHomeDataFromNet()
    {
        String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new VideoActivity.MyStringCallback());
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
                processJson(response);
                adapter = new VideoAdapter(VideoActivity.this,resultBean);
                recyclerView.setAdapter(adapter);
            }
        }

        @Override
        public void inProgress(float progress, long total, int id)
        {
            Log.e(TAG, "inProgress:" + progress);
        }
    }

    private void processJson(String response) {
        JsonObject object = new JsonParser().parse(response).getAsJsonObject();
        JsonObject jsonElement = object.getAsJsonObject("result");
        Gson gson = new Gson();
        resultBean = gson.fromJson(jsonElement,HomeBean.ResultBean.class);
    }
}
