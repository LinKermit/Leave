package com.android.wang.rxjavalearn.retrofit;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.wang.rxjavalearn.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2016/11/7.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private List<GankBean.GankResult> mDatas;
    private List<Integer> mHeights;
    private List<Integer> mHeights_image;
    private Context context;
    //点击和长按事件
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(ImageAdapter.OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public ImageAdapter(List<GankBean.GankResult> mDatas, Context context) {
        this.mDatas = mDatas;
        this.context = context;
        mInflater = LayoutInflater.from(context);
        //随机高度
        mHeights = new ArrayList<Integer>();
        mHeights_image = new ArrayList<Integer>();
        for (int i = 0; i < mDatas.size(); i++)
        {
            int height = (int) (350 + Math.random() * 150);
            mHeights.add(height);
            mHeights_image.add(height-150);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater
                .inflate(R.layout.item_image, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ViewGroup.LayoutParams lp = holder.cardview.getLayoutParams();
        lp.height = mHeights.get(position);
        holder.cardview.setLayoutParams(lp);
        //首先利用getLayoutParams()方法，获取控件的LayoutParams
        /*
            设置该控件的layoutParams参数
            eg: laParams.height=200;
                laParams.width=100;
         */
       //设置imageview高度
//        ViewGroup.LayoutParams lp_image = holder.imageView.getLayoutParams();
//        lp_image.height = mHeights_image.get(position);
//        holder.imageView.setLayoutParams(lp_image);


        GankBean.GankResult imageDetail = mDatas.get(position);
        holder.tv.setText(imageDetail.getSource());
        holder.tv2.setText(imageDetail.getWho());
        Glide.with(context)
                .load(imageDetail.getUrl())
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(holder.imageView);

        //如果设置了回调。则设置点击事件
        if (mOnItemClickLitener!=null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView,pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
    CardView cardview;
    TextView tv;
        TextView tv2;
    ImageView imageView;
    public MyViewHolder(View itemView) {
        super(itemView);
        tv = (TextView) itemView.findViewById(R.id.item_title);
        tv2 = (TextView) itemView.findViewById(R.id.item_title2);
        imageView = (ImageView) itemView.findViewById(R.id.item_img);
        cardview = (CardView) itemView.findViewById(R.id.cardview);
        }
    }

}



