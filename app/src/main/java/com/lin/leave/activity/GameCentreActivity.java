package com.lin.leave.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.lin.leave.R;
import com.lin.leave.adapter.GameCenterAdapter;
import com.lin.leave.base.BaseActivity;
import com.lin.leave.bean.GameCenterInfo;
import com.lin.leave.down.DownloadService;
import com.lin.leave.fragment.HomeFragment;
import com.lin.leave.utils.Constants;
import com.lin.leave.view.DividerItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

public class GameCentreActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private RelativeLayout rl_loading;
    private GameCenterInfo centerInfo;
    GameCenterAdapter adapter;

    private static final String TAG = "GameCentreActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_centre);
        initView();
        setToolBar();
        initData();
        //启动并绑定服务
        startDownService();
    }

    private void startDownService() {
        Intent intent = new Intent(this,DownloadService.class);
        startService(intent);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycle_down);
        rl_loading = (RelativeLayout) findViewById(R.id.rl_loading);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GameCenterAdapter(this,null);
        recyclerView.setAdapter(adapter);
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("游戏中心");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initData() {
        getDataFromNet();
    }

    public void getDataFromNet()
    {
        String url = Constants.GAME_URL;
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
            rl_loading.setVisibility(View.GONE);
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
                adapter = new GameCenterAdapter(GameCentreActivity.this,centerInfo);
                recyclerView.setAdapter(adapter);

                adapter.setOnDownLoadListener(new GameCenterAdapter.OnDownLoadListener() {
                    @Override
                    public void onStart(View view, int position) {
                        String url = "http://192.168.1.111:8080/GoodMovies.mp4";
                        downBinder.startDownload(url);
                    }

                    @Override
                    public void onStop(View view, int position) {
                        downBinder.pauseDownload();
                    }

                    @Override
                    public void onCancel(View view, int position) {
                        downBinder.cancelDownload();
                    }
                });
            }
        }

        @Override
        public void inProgress(float progress, long total, int id)
        {
            Log.e(TAG, "inProgress:" + progress);
            rl_loading.setVisibility(View.VISIBLE);
        }
    }

    private void processJson(String response) {
        Gson gson = new Gson();
        centerInfo = gson.fromJson(response,GameCenterInfo.class);
        Log.d(TAG, "processJson: " + centerInfo.getItems().get(2).getTitle());
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() ==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * 下载任务
     */

    private DownloadService.DownBinder downBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downBinder = (DownloadService.DownBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
