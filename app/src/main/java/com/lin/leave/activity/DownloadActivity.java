package com.lin.leave.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lin.leave.R;
import com.lin.leave.adapter.GameCenterAdapter;
import com.lin.leave.adapter.home.DownloadAdapter;
import com.lin.leave.base.BaseActivity;
import com.lin.leave.utils.CommonUtil;
import com.lin.leave.view.CustomEmptyView;
import com.lin.leave.view.DividerItemDecoration;
import com.lin.leave.view.NumberProgress;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lin on 2017/5/10.
 */

public class DownloadActivity extends BaseActivity {
    private static final String TAG = "DownloadActivity";
    TextView cache_size_text;
    NumberProgress num_progress;
    private RecyclerView recyclerView;
    DownloadAdapter adapter;
    private CustomEmptyView customEmptyView;

    //隐藏控件
    CheckBox check_all;
    RelativeLayout rl_delete;
    Button bt_delete;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        setToolBar();
        initView();
        initData();
    }

    private void initData() {
        readFile();
    }

    private void initView() {
        check_all = (CheckBox) findViewById(R.id.check_all);
        rl_delete = (RelativeLayout) findViewById(R.id.rl_delete);
        bt_delete = (Button) findViewById(R.id.bt_delete);
        customEmptyView = (CustomEmptyView) findViewById(R.id.empty_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_download);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));

        cache_size_text = (TextView) findViewById(R.id.cache_size_text);
        num_progress = (NumberProgress) findViewById(R.id.num_progress);

        long phoneTotalSize = CommonUtil.getPhoneTotalSize();//手机存储的总容量
        long phoneAvailableSize = CommonUtil.getPhoneAvailableSize();
        int value = countProgress(phoneTotalSize,phoneAvailableSize);
        num_progress.setValue(value);

        //转换为G的显示单位
        String totalSizeStr = Formatter.formatFileSize(this, phoneTotalSize);
        String availabSizeStr = Formatter.formatFileSize(this, phoneAvailableSize);
        cache_size_text.setText("主存储:" + totalSizeStr + "/" + "可用:" + availabSizeStr);
    }

    private int countProgress(long phoneTotalSize, long phoneAvailableSize) {

        double totalSize = phoneTotalSize / (1024 * 3);
        double availabSize = phoneAvailableSize / (1024 * 3);
        //取整相减
        int size = (int) (Math.floor(totalSize) - Math.floor(availabSize));
        double v = (size / Math.floor(totalSize)) * 100;
        return (int) Math.floor(v);
    }


    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("离线缓存");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() ==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * 读取下载文件夹目录下的文件
     */
    private void readFile(){
        File[] currentFiles;
        if (CommonUtil.checkSdCard()){
            //下载目录
            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            File file = new File(directory);
            if (file.exists()){
                currentFiles = file.listFiles();
                inflateListView(currentFiles);
            }
        }
    }

    private void inflateListView(final File[] files) {
        final List<Map<String,Object>> mapList = new ArrayList<>();
        for (int i=0;i<files.length;i++){
            Map<String,Object> fileMap = new HashMap<>();
            double fileSize = files[i].length() / (1024 * 3);

            fileMap.put("fileName",files[i].getName());
            fileMap.put("fileSize",fileSize);
            fileMap.put("filePath",files[i].getPath());
            mapList.add(fileMap);
        }

        if (mapList!=null&&mapList.size()>0){
            customEmptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new DownloadAdapter(DownloadActivity.this,mapList,check_all,bt_delete);
            recyclerView.setAdapter(adapter);

            adapter.setOnRecycleItemListener(new DownloadAdapter.OnRecycleItemListener() {
                @Override
                public void onItemLongClick(final int position) {
                    new AlertDialog.Builder(DownloadActivity.this)
                            .setTitle("Delete")
                            .setMessage("Are you sure")
                            .setIcon(R.drawable.ic__pop_group)
                            .setPositiveButton("sure", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mapList.remove(position);//内存中删除
                                    files[position].delete();//本地
                                    adapter.notifyItemRemoved(position);
                                }
                            })
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            });
        }else {
            showEmptyView();
        }
    }

    /**
     * 如果本地没有数据
     */
    private void showEmptyView(){
        customEmptyView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        customEmptyView.setmEmptyImg(R.drawable.img_tips_error_load_error);
        customEmptyView.setmEmptyText("加载失败~(≧▽≦)~啦啦啦.");
    }
}
