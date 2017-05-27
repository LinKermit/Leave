package com.lin.leave.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lin on 2017/4/7.
 * fragment+viewpager懒加载 基类
 */

public abstract class BaseFragment extends Fragment {

    public Activity mContext;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.mContext = activity;
    }

    private boolean isVisible = false;//当前Fragment是否可见
    private boolean isInitView = false;//是否与View建立起映射关系
    private boolean isFirstLoad = true;//是否是第一次加载数据

    private View convertView;
    private SparseArray<View> mViews;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        convertView = initView(inflater, container, savedInstanceState);
        mViews = new SparseArray<>();
        isInitView = true;
        lazyLoadData();
        return convertView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoadData();

        } else {
            isVisible = false;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void lazyLoadData() {

        if (!isFirstLoad || !isVisible || !isInitView) {
            return;
        }

        initData();
        isFirstLoad = false;
    }

    /**
     * 让布局中的view与fragment中的变量建立起映射
     * 布局+view初始化
     */
    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 加载要显示的数据
     */
    protected abstract void initData();
//    /**
//     * fragment中可以通过这个方法直接找到需要的view，而不需要进行类型强转
//     * @param viewId
//     * @param <E>
//     * @return
//     */
//    protected <E extends View> E findView(int viewId) {
//        if (convertView != null) {
//            E view = (E) mViews.get(viewId);
//            if (view == null) {
//                view = (E) convertView.findViewById(viewId);
//                mViews.put(viewId, view);
//            }
//            return view;
//        }
//        return null;
//    }
}
