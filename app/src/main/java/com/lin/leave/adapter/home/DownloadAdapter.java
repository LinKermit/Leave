package com.lin.leave.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.lin.leave.R;
import com.lin.leave.activity.SystemVideoActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by Lin on 2017/5/10.
 * 下载文件夹适配器
 */

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.DownloadViewHolder>{

    private Context mContext;
    private List<Map<String, Object>> mapList;
    CheckBox check_all;
    Button bt_delete;
    public DownloadAdapter(Context mContext, List<Map<String, Object>> mapList, CheckBox check_all, Button bt_delete) {
        this.mContext = mContext;
        this.mapList = mapList;
        this.check_all = check_all;
        this.bt_delete = bt_delete;
    }

    @Override
    public DownloadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_download,null);
        return new DownloadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DownloadViewHolder holder, final int position) {
        if (mapList!=null&&mapList.size()>0){
            Map<String, Object> mapValue = mapList.get(position);
            final String fileName = String.valueOf(mapValue.get("fileName"));
            final String fileSize = String.valueOf(mapValue.get("fileSize"));
            final String filePath = String.valueOf(mapValue.get("filePath"));
            holder.tv_title.setText(fileName+"("+fileSize+"kb"+")");
            holder.tv_path.setText(filePath);

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onRecycleItemListener!=null){
                        onRecycleItemListener.onItemLongClick(position);
                    }
                    return true;
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, SystemVideoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("fileName",fileName);
                    bundle.putString("fileSize",fileSize);
                    bundle.putString("filePath",filePath);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mapList.size();
    }


    public class DownloadViewHolder extends RecyclerView.ViewHolder{

        public ImageView iv_image;
        public TextView tv_title;
        public TextView tv_path;
        CheckBox checkBox;
        public DownloadViewHolder(View itemView) {
            super(itemView);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_path = (TextView) itemView.findViewById(R.id.tv_path);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);

        }
    }

    public interface OnRecycleItemListener{
        void onItemLongClick(int position);
    }

    public void setOnRecycleItemListener(OnRecycleItemListener onRecycleItemListener) {
        this.onRecycleItemListener = onRecycleItemListener;
    }

    OnRecycleItemListener onRecycleItemListener;
}
