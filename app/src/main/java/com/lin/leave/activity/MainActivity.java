package com.lin.leave.activity;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lin.leave.R;
import com.lin.leave.base.BaseActivity;
import com.lin.leave.fragment.HomeRegionFragment;
import com.lin.leave.fragment.TypeFragment;
import com.lin.leave.fragment.HomeFragment;
import com.lin.leave.fragment.SearchFragment;
import com.lin.leave.fragment.UserFragment;
import com.lin.leave.fragment.VideoFragment;
import com.lin.leave.themeUtils.CardPickerDialog;
import com.lin.leave.utils.CacheUtils;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ActionBar actionBar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    List<Fragment> fragmentList;
    List<String> fragmentNames;
    private ImageView iv_menu;
    private ImageView mSwitchMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setToolBar();
        initData();
    }

    private void initView() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tab);
        navigationView.setCheckedItem(R.id.nav_theme);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        mSwitchMode = (ImageView) headerView.findViewById(R.id.iv_head_switch_mode);
        boolean flag = CacheUtils.getNight(this,"isNight");
        if (flag) {
            mSwitchMode.setImageResource(R.drawable.ic_switch_daily);
        } else {
            mSwitchMode.setImageResource(R.drawable.ic_switch_night);
        }
        mSwitchMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchNight();
            }
        });
    }

    private void switchNight() {
        boolean isNight = CacheUtils.getNight(MainActivity.this,"isNight");
        if (isNight){
            // 日间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            mSwitchMode.setImageResource(R.drawable.ic_switch_night);
            CacheUtils.putNight(MainActivity.this,"isNight",false);
        }else {
            // 夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            mSwitchMode.setImageResource(R.drawable.ic_switch_daily);
            CacheUtils.putNight(MainActivity.this,"isNight",true);
        }
        recreate();
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new VideoFragment());
        fragmentList.add(new HomeRegionFragment());
        fragmentList.add(new TypeFragment());
        fragmentList.add(new UserFragment());
        fragmentList.add(new SearchFragment());


        fragmentNames = new ArrayList<>();
        fragmentNames.add("主页");
        fragmentNames.add("视频");
        fragmentNames.add("推荐");
        fragmentNames.add("分区");
        fragmentNames.add("个人");
        fragmentNames.add("发现");

        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_theme:
                CardPickerDialog dialog = new CardPickerDialog();
                dialog.setClickListener(MainActivity.this);
                dialog.show(getSupportFragmentManager(), CardPickerDialog.TAG);
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_vip:
                Intent intent = new Intent(MainActivity.this,VipActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_download:
                Intent intent1 = new Intent(MainActivity.this,DownloadActivity.class);
                startActivity(intent1);
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_shop:
                Intent intent2 = new Intent(MainActivity.this,DetailActivity.class);
                startActivity(intent2);
                drawerLayout.closeDrawers();
                break;

        }
        return true;
    }

    class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
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

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setTitle("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//            case android.R.id.home:
//                drawerLayout.openDrawer(GravityCompat.START);
//                break;
            case R.id.game:
                Intent intent = new Intent(this, GameCentreActivity.class);
                startActivity(intent);
                break;
            case R.id.down:
                Intent intent1 = new Intent(this, DownloadActivity.class);
                startActivity(intent1);
                break;
            case R.id.search:
                Toast.makeText(this,"search",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private long exitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis() - exitTime>2000){
                Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }else {
                finish();
            }
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * Activity被意外回收，重写此方法中保存需要保存的数据
     * @param outState
     * @param outPersistentState
     */
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    /**
     * 可以从中提取保存好的数据.

     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
