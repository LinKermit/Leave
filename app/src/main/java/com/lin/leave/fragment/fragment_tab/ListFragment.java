package com.lin.leave.fragment.fragment_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lin.leave.R;
import com.lin.leave.base.BaseFragment;

/**
 * Created by Lin on 2017/5/12.
 */

public class ListFragment extends BaseFragment {
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        return view;
    }

    @Override
    protected void initData() {

    }
}
