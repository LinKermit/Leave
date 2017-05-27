package com.lin.leave.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lin.leave.R;
import com.lin.leave.bean.HomeBean;
import com.lin.leave.utils.Constants;

/**
 * Created by Lin on 2017/5/9.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    Context mContext;
    HomeBean.ResultBean resultBean;
    public VideoAdapter(Context mContext, HomeBean.ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_video,null);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        if (resultBean!=null){
            HomeBean.ResultBean.RecommendInfoBean infoBean = resultBean.getRecommend_info().get(position);
            holder.tv_video_1.setText(infoBean.getCover_price());
//        holder.tv_video_2.setText(infoBean.getProduct_id());
            holder.tv_video_title.setText(infoBean.getName());
            Glide.with(mContext).load(Constants.BASE_URl_IMAGE +infoBean.getFigure())
                    .into(holder.iv_video_right);
        }

    }

    @Override
    public int getItemCount() {
        return resultBean.getRecommend_info().size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv_video_right;
        private TextView tv_video_2;
        private TextView tv_video_1;
        private TextView tv_video_title;

        public VideoViewHolder(View itemView) {
            super(itemView);
            iv_video_right = (ImageView) itemView.findViewById(R.id.iv_video_right);
            tv_video_2 = (TextView) itemView.findViewById(R.id.tv_video_2);
            tv_video_1 = (TextView) itemView.findViewById(R.id.tv_video_1);
            tv_video_title = (TextView) itemView.findViewById(R.id.tv_video_title);
        }
    }
}
