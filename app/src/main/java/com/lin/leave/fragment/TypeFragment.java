package com.lin.leave.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lin.leave.R;
import com.lin.leave.adapter.home.TypeListAdapter;
import com.lin.leave.adapter.home.TypeRightAdapter;
import com.lin.leave.base.BaseFragment;
import com.lin.leave.bean.TypeBean;
import com.lin.leave.utils.Constants;
import com.lin.leave.utils.FileCacheUtils;
import com.lin.leave.view.DividerItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Lin on 2017/5/5.
 */

public class TypeFragment extends BaseFragment {
    private boolean isFirst = true;
    private static final String TAG = "TypeFragment";
    private ListView listView;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private int urlPosition = 0;
    private String[] urls = new String[]{Constants.SKIRT_URL, Constants.JACKET_URL, Constants.PANTS_URL, Constants.OVERCOAT_URL,
            Constants.ACCESSORY_URL, Constants.BAG_URL, Constants.DRESS_UP_URL, Constants.HOME_PRODUCTS_URL, Constants.STATIONERY_URL,
            Constants.DIGIT_URL, Constants.GAME2_URL};
    private List<TypeBean.ResultBean> result;
    private TypeListAdapter listAdapter;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.fragment_tablayout,null);
        listView = (ListView) view.findViewById(R.id.listView);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_tab);
        manager = new GridLayoutManager(mContext,1);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setLayoutManager(manager);
        return view;
    }

    @Override
    protected void initData() {

        String cache = FileCacheUtils.getString(mContext,urls[urlPosition]);
        if (!TextUtils.isEmpty(cache)){
            processData(cache);
        }else {
            getDataFromNet(urls[0]);
        }


    }

    public void getDataFromNet(String url) {
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {


        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void onAfter(int id) {
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            //两位请求成功

            switch (id) {
                case 100:
                    if (response != null) {
                        //解析数据
                        FileCacheUtils.putString(mContext,urls[urlPosition],response);
                        processData(response);

                    }
                    break;
                case 101:
                    Toast.makeText(mContext, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }


    private void initListViewListener(final TypeListAdapter listAdapter) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listAdapter.changeSelected(position);//刷新
                if (position != 0) {
                    isFirst = false;
                }
                urlPosition = position;
                listAdapter.notifyDataSetChanged();


                String cache = FileCacheUtils.getString(mContext,urls[urlPosition]);
                if (!TextUtils.isEmpty(cache)){
                    processData(cache);
                }else {
                    getDataFromNet(urls[position]);
                }


            }
        });

        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listAdapter.changeSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void processData(String json) {
        Gson gson = new Gson();
        TypeBean typeBean = gson.fromJson(json, TypeBean.class);
        result = typeBean.getResult();

        if (isFirst){
            listAdapter = new TypeListAdapter(mContext);
            listView.setAdapter(listAdapter);
        }


        initListViewListener(listAdapter);
        //解析右边数据
        TypeRightAdapter rightAdapter = new TypeRightAdapter(mContext, result,urls[urlPosition]);
        recyclerView.setAdapter(rightAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
