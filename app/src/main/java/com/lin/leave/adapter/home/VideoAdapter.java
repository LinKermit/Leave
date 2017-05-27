package com.lin.leave.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lin.leave.R;
import com.lin.leave.bean.VideoBean;
import com.lin.leave.view.CircleImageView;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Lin on 2017/5/10.
 */

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context mContext;
    private VideoBean videoBean;
    List<VideoBean.ItemsBean> itemsBeen;
    public VideoAdapter(Context mContext, VideoBean videoBean) {
        this.mContext = mContext;
        this.videoBean = videoBean;
        if (videoBean!=null){
            itemsBeen = videoBean.getItems();
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_fragment_video,null);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (videoBean!=null&& itemsBeen.size()>0){
            VideoViewHolder holder1 = new VideoViewHolder(holder.itemView);


            holder1.jcVideo.setUp(itemsBeen.get(position).getDownload_link(), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                    itemsBeen.get(position).getTitle());
            Glide.with(mContext)
                    .load(itemsBeen.get(position).getCover())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.ic_hotbitmapgg_avatar)
                    .into(holder1.jcVideo.thumbImageView);
            holder1.tv_title.setText(itemsBeen.get(position).getSummary());
//            holder1.tv_text.setText(itemsBeen.get(position).getIOS_pkg_size()+"");
        }
    }

    @Override
    public int getItemCount() {
        return videoBean!=null?videoBean.getItems().size():0;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        JCVideoPlayerStandard jcVideo;
        CircleImageView iv_img;
        TextView tv_title;
        TextView tv_text;
        TextView tv_com;
        public VideoViewHolder(View itemView) {
            super(itemView);
            jcVideo = (JCVideoPlayerStandard) itemView.findViewById(R.id.jc_video);
            iv_img = (CircleImageView) itemView.findViewById(R.id.iv_image);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_text = (TextView) itemView.findViewById(R.id.tv_text);
            tv_com = (TextView) itemView.findViewById(R.id.tv_com);
        }
    }
}
