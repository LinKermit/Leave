package com.lin.leave.fragment.region;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lin.leave.R;
import com.lin.leave.activity.VideoActivity;
import com.lin.leave.adapter.VideoAdapter;
import com.lin.leave.base.BaseFragment;
import com.lin.leave.bean.HomeBean;
import com.lin.leave.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Lin on 2017/5/15.
 */

public class IntroFragment extends BaseFragment {
    private static final String TAG = "IntroFragment";
    TextView tv_title;
    TextView tv_description;
    RecyclerView recycle_intro;
    private HomeBean.ResultBean resultBean;
    private VideoAdapter adapter;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro,container,false);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_description = (TextView) view.findViewById(R.id.tv_description);
        recycle_intro = (RecyclerView) view.findViewById(R.id.recycle_intro);
        LinearLayoutManager manager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recycle_intro.setLayoutManager(manager);
        return view;
    }

    @Override
    protected void initData() {
        getHomeDataFromNet();
    }

    public void getHomeDataFromNet()
    {
        String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new IntroFragment.MyStringCallback());
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
            tv_title.setText(resultBean.getRecommend_info().get(0).getName());
            tv_description.setText(resultBean.getRecommend_info().get(0).getName());
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
                adapter = new VideoAdapter(mContext,resultBean);
                recycle_intro.setAdapter(adapter);
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
