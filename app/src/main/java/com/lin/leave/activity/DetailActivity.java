package com.lin.leave.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lin.leave.R;
import com.lin.leave.base.BaseActivity;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Lin on 2017/5/11.
 */

public class DetailActivity extends BaseActivity {
    NestedScrollView nestedScrollView;
    private ImageView image_bg;
    Toolbar toolbar;
    private TextView tv_check;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setToolBar();
        initView();
    }

    private void initView() {
        nestedScrollView = (NestedScrollView)findViewById(R.id.nestedScrollView);
        image_bg = (ImageView) findViewById(R.id.image_bg);
        tv_check = (TextView) findViewById(R.id.tv_check);
        //设置Toolbar的透明度
        toolbar.setBackgroundColor(Color.argb(0, 251, 114, 153));

        final float toolBarHeight = getResources().getDimension(R.dimen.image_default_height);
        final float imageHeight = getResources().getDimension(R.dimen.action_bar_default_height);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                float offsetY = toolBarHeight - imageHeight;
                //计算滑动距离的偏移量
                float offset = 1 - Math.max((offsetY - scrollY) / offsetY, 0f);
                float absOffset = Math.abs(offset);
                //如果滑动距离大于1就设置完全不透明度
                if (absOffset >= 1) {
                    absOffset = 1;
                }
                toolbar.setBackgroundColor(Color.argb((int) (absOffset * 255), 251, 114, 153));
            }
        });

        //设置背景高斯模糊图片
        Glide.with(this)
                .load(R.drawable.ic_hotbitmapgg_avatar)
                .bitmapTransform(new BlurTransformation(this))
                .into(image_bg);
    }

    private void setToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("番剧详情");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() ==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
