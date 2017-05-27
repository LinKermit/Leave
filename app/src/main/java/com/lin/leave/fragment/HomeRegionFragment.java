package com.lin.leave.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lin.leave.R;
import com.lin.leave.activity.AppLayoutActivity;
import com.lin.leave.activity.GameCentreActivity;
import com.lin.leave.adapter.home.HomeRegionAdapter;
import com.lin.leave.base.BaseFragment;

/**
 * Created by Lin on 2017/5/5.
 */

public class HomeRegionFragment extends BaseFragment {
    private RecyclerView recyclerView;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.fragment_home_region,null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_region);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        HomeRegionAdapter mAdapter = new HomeRegionAdapter(mContext);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new HomeRegionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 5:
                        Intent intent = new Intent(mContext, GameCentreActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(mContext,AppLayoutActivity.class);
                        mContext.startActivity(intent1);
                }

            }
        });
        return view;
    }

    @Override
    protected void initData() {

    }
}
