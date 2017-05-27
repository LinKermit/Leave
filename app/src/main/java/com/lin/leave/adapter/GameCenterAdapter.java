package com.lin.leave.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lin.leave.R;
import com.lin.leave.bean.GameCenterInfo;

import java.util.List;

/**
 * Created by Lin on 2017/5/8.
 * 游戏中心适配器
 */

public class GameCenterAdapter extends RecyclerView.Adapter<GameCenterAdapter.GameViewHolder> {

    private static final int HEADER = 0;

    private Context mContext;
    private GameCenterInfo info;

    public GameCenterAdapter(Context mContext, GameCenterInfo info) {
        this.mContext =  mContext;
        this.info = info;
    }
    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(mContext, R.layout.item_game_center,null);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GameViewHolder holder, final int position) {
        if (info!=null){
            List<GameCenterInfo.ItemsBean> items = info.getItems();
            holder.tv_title.setText(items.get(position).getTitle());
            holder.tv_content.setText(items.get(position).getSummary());
            holder.item_btn.setText("下载");
            if (onDownLoadListener!=null){
                holder.item_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.item_btn.getText() == "下载"){
                            onDownLoadListener.onStart(holder.item_btn,position);
                            holder.item_btn.setText("暂停");
                        }else if (holder.item_btn.getText() == "暂停"){
                            onDownLoadListener.onStop(holder.item_btn,position);
                            holder.item_btn.setText("下载");
                        }

                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return info!=null?info.getItems().size():0;
    }

    public class GameViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_image;
        TextView tv_title;
        TextView tv_content;
        Button item_btn;

        public GameViewHolder(View itemView) {
            super(itemView);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            item_btn = (Button) itemView.findViewById(R.id.item_btn);
        }
    }

    public interface OnDownLoadListener{
        void onStart(View view,int position);
        void onStop(View view,int position);
        void onCancel(View view,int position);
    }

    public void setOnDownLoadListener(OnDownLoadListener onDownLoadListener) {
        this.onDownLoadListener = onDownLoadListener;
    }

    OnDownLoadListener onDownLoadListener;
}
