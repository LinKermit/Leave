package com.lin.leave.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lin.leave.R;

/**
 * Created by Lin on 2017/5/8.
 * 分区适配器
 */

public class HomeRegionAdapter extends RecyclerView.Adapter<HomeRegionAdapter.RegionViewHolder> {

    private String[] itemNames = new String[] {
            "直播", "番剧", "动画",
            "音乐", "舞蹈", "游戏",
            "科技", "生活", "鬼畜",
            "时尚", "广告", "娱乐",
            "电影", "电视剧", "游戏中心",
    };

    private int[] itemIcons = new int[] {
            R.drawable.ic_category_live, R.drawable.ic_category_t13,
            R.drawable.ic_category_t1, R.drawable.ic_category_t3,
            R.drawable.ic_category_t129, R.drawable.ic_category_t4,
            R.drawable.ic_category_t36, R.drawable.ic_category_t160,
            R.drawable.ic_category_t119, R.drawable.ic_category_t155,
            R.drawable.ic_category_t165, R.drawable.ic_category_t5,
            R.drawable.ic_category_t23, R.drawable.ic_category_t11,
            R.drawable.ic_category_game_center
    };
    private Context mContext;

    public HomeRegionAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RegionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext,R.layout.item__fragment_region,null);
        return new RegionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RegionViewHolder holder, final int position) {
        holder.item_img.setImageResource(itemIcons[position]);
        holder.item_text.setText(itemNames[position]);

        if (onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.itemView,position);//设置view的回调
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemNames.length;
    }

    public class RegionViewHolder extends RecyclerView.ViewHolder{

        ImageView item_img;
        TextView item_text;
        public RegionViewHolder(View itemView) {
            super(itemView);
            item_img = (ImageView) itemView.findViewById(R.id.item_img);
            item_text = (TextView) itemView.findViewById(R.id.item_text);
        }
    }


    //接口
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    //接口回调
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

}
