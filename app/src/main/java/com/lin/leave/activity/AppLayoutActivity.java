package com.lin.leave.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.lin.leave.R;
import com.lin.leave.base.BaseActivity;
import com.lin.leave.fragment.region.CommendFragment;
import com.lin.leave.fragment.region.IntroFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lin on 2017/5/15.
 */

public class AppLayoutActivity extends BaseActivity {

    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    TextView mTvPlayer;
    TextView mAvText;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> fragmentNames = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_layout);
        initView();
        setToolBar();

    }



    private void initView() {
        mTvPlayer = (TextView) findViewById(R.id.tv_player);
        mAvText = (TextView) findViewById(R.id.tv_av);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0){
                    mTvPlayer.setVisibility(View.GONE);
                    mAvText.setVisibility(View.VISIBLE);
                }else if (Math.abs(verticalOffset)>= appBarLayout.getTotalScrollRange()){//判断滑动的长度是否大于appBarLayout
                    //折叠状态
                    mTvPlayer.setVisibility(View.VISIBLE);
                    mAvText.setVisibility(View.GONE);
                }else {
                    mTvPlayer.setVisibility(View.GONE);
                    mAvText.setVisibility(View.VISIBLE);

                }
            }
        });

        initFragment();
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(),fragmentList,fragmentNames));
        slidingTabLayout.setViewPager(viewPager);
        measureTabLayoutTextWidth(0);
    }

    private void initFragment() {
        IntroFragment introFragment = new IntroFragment();
        CommendFragment commendFragment = new CommendFragment();
        fragmentList.add(introFragment);
        fragmentList.add(commendFragment);
        fragmentNames.add("简介");
        fragmentNames.add("评论" + "(" + "123" + ")");
    }

    private void setToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mAvText.setText("视频详情");
    }

    class MyFragmentAdapter extends FragmentPagerAdapter{
        List<Fragment> fragmentList;
        List<String> fragmentNames;
        public MyFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> fragmentNames) {
            super(fm);
            this.fragmentList = fragmentList;
            this.fragmentNames = fragmentNames;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentNames.get(position);
        }
    }

    private void measureTabLayoutTextWidth(int position) {
        String title = fragmentNames.get(position);
        TextView textView = slidingTabLayout.getTitleView(position);
        TextPaint mPaint = textView.getPaint();
        float width = mPaint.measureText(title);
        slidingTabLayout.setIndicatorWidth(width);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_video,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
