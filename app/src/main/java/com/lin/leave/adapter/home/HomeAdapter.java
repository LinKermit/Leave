package com.lin.leave.adapter.home;

import android.content.Context;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.lin.leave.R;
import com.lin.leave.bean.HomeBean;
import com.lin.leave.utils.Constants;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnLoadImageListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lin on 2017/5/5.
 * 主页加载不同布局
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BANNER = 0;
    private static final int BODY = 1;
    private static final int TOPIC = 2;
    private static final int FOOT = 3;


    LayoutInflater inflater;
    private Context mContext;
    private HomeBean.ResultBean resultBean;
    public HomeAdapter(Context mContext,HomeBean.ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case BANNER:
                View view0 = inflater.inflate(R.layout.item_home_banner,null);
                return new BannerViewHolder(view0);
            case BODY:
                View view1 = inflater.inflate(R.layout.item_home_body,null);
                return new BodyViewHolder(view1);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (resultBean!=null){
            switch (getItemViewType(position)){
                case BANNER:
                    BannerViewHolder holder0 = (BannerViewHolder) holder;
                    holder0.setData(holder0);
                    break;
                case BODY:
                    BodyViewHolder holder1 = (BodyViewHolder) holder;
                    holder1.setData(holder1);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == BANNER){
            return BANNER;
        }else if (position == BODY){
            return BODY;
        }else if (position == TOPIC){
            return TOPIC;
        }else {
            return FOOT;
        }
    }


    /**
     * 横幅广告
     */
    public class BannerViewHolder extends RecyclerView.ViewHolder{

        public Banner banner;
        public BannerViewHolder(View itemView) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.banner);
        }

        private void setData(BannerViewHolder holder0) {
            List<String> imageUrls = new ArrayList<>();
            for (int i=0; i<resultBean.getBanner_info().size(); i++){
                String imageUrl = resultBean.getBanner_info().get(i).getImage();
                imageUrls.add(imageUrl);
            }
            holder0.banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
            holder0.banner.setBannerAnimation(Transformer.Accordion);
            holder0.banner.setImages(imageUrls, new OnLoadImageListener() {
                @Override
                public void OnLoadImage(ImageView view, Object url) {
                    Glide.with(mContext).load(Constants.BASE_URl_IMAGE + url)
                            .into(view);
                }
            });
            holder0.banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                }
            });
        }
    }

    public class BodyViewHolder extends RecyclerView.ViewHolder{
        public GridView gridView;
        public BodyViewHolder(View itemView) {
            super(itemView);
            gridView = (GridView) itemView.findViewById(R.id.gd_home_body);
        }
        public void setData(BodyViewHolder holder1) {
            holder1.gridView.setAdapter(new BodyAdapter(mContext,resultBean.getHot_info()));
        }
    }
}
