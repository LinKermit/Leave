package com.lin.leave.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lin.leave.R;
import com.lin.leave.base.BaseFragment;
import com.lin.leave.bean.HotSearchBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lin on 2017/5/5.
 */

public class SearchFragment extends BaseFragment {

    private TagFlowLayout hide_tags_layout;
    private TagFlowLayout tags_layout;
    NestedScrollView hide_scroll_view;
    LinearLayout more_layout;
    TextView tv_more;
    private static boolean isShow = true;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.fragment_search,null);
        hide_tags_layout = (TagFlowLayout) view.findViewById(R.id.hide_tags_layout);
        tags_layout = (TagFlowLayout) view.findViewById(R.id.tags_layout);
        hide_scroll_view = (NestedScrollView) view.findViewById(R.id.hide_scroll_view);
        more_layout = (LinearLayout) view.findViewById(R.id.more_layout);
        tv_more = (TextView) view.findViewById(R.id.tv_more);
        return view;
    }

    @Override
    protected void initData() {

        List<HotSearchBean.ListBean> searchBean = getHotTag();
        tags_layout.setAdapter(new TagAdapter<HotSearchBean.ListBean>(searchBean) {//抽象类TagAdapter<HotSearchBean.ListBean>
            @Override
            public View getView(FlowLayout parent, int position, HotSearchBean.ListBean listBean) {
                TextView mTags = (TextView) LayoutInflater.from(getActivity())
                        .inflate(R.layout.layout_tags_item, parent, false);
                mTags.setText(listBean.getKeyword());
                mTags.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                return mTags;
            }
        });

        hide_tags_layout.setAdapter(new TagAdapter<HotSearchBean.ListBean>(searchBean) {//抽象类TagAdapter<HotSearchBean.ListBean>
            @Override
            public View getView(FlowLayout parent, int position, HotSearchBean.ListBean listBean) {
                TextView mTags = (TextView) LayoutInflater.from(getActivity())
                        .inflate(R.layout.layout_tags_item, parent, false);
                mTags.setText(listBean.getKeyword());
                mTags.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                return mTags;
            }

        });


        more_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow){
                    hide_scroll_view.setVisibility(View.VISIBLE);
                    tv_more.setText("收起");
                    isShow = false;
                    Drawable upDrawable = mContext.getDrawable(R.drawable.ic_arrow_up_gray_round);
                    upDrawable.setBounds(0, 0, upDrawable.getMinimumWidth(), upDrawable.getMinimumHeight());
                    tv_more.setCompoundDrawables(upDrawable,null,null,null);
                }else {
                    hide_scroll_view.setVisibility(View.GONE);
                    tv_more.setText("查看更多");
                    isShow = true;
                    Drawable upDrawable = mContext.getDrawable(R.drawable.ic_arrow_down_gray_round);
                    upDrawable.setBounds(0, 0, upDrawable.getMinimumWidth(), upDrawable.getMinimumHeight());
                    tv_more.setCompoundDrawables(upDrawable,null,null,null);
                }
            }
        });

    }

    private List<HotSearchBean.ListBean> getHotTag(){
        List<HotSearchBean.ListBean> searchBean = new ArrayList<>();
        for (int i=0; i<8; i++){
            HotSearchBean.ListBean hotTag = new HotSearchBean.ListBean();
            hotTag.setKeyword("极乐净土"+i);
            hotTag.setStatus("keep");
            searchBean.add(hotTag);
        }
        return searchBean;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
