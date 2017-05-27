package com.lin.leave.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lin.leave.R;
import com.lin.leave.base.BaseFragment;

/**
 * Created by Lin on 2017/5/5.
 */

public class UserFragment extends BaseFragment {

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.fragment_home,null);
        return view;
    }

    @Override
    protected void initData() {

    }
}
