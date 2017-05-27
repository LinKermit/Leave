package com.lin.leave.adapter.home;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.lin.leave.R;
import com.lin.leave.bean.TypeBean;
import com.lin.leave.utils.Constants;

import java.util.List;

/**
 * Created by Lin on 2017/5/12.
 */

public class TypeRightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<TypeBean.ResultBean> result;

    /**
     * 常用分类
     */
    private List<TypeBean.ResultBean.ChildBean> childList;
    /**
     * 热卖商品列表的数据
     */
    private List<TypeBean.ResultBean.HotProductListBean> hot_product_list;
    /**
     * 普通的
     */
    public static final int CHILD = 0;
    /**
     * 热卖
     */
    public static final int HOT = 1;

    String url;
    private int CURRENT = 0;
    public TypeRightAdapter(Context mContext, List<TypeBean.ResultBean> result, String url) {
        this.mContext = mContext;
        this.result = result;
        this.url = url;
        if (result != null && result.size() > 0) {
            childList = result.get(0).getChild();
            hot_product_list = result.get(0).getHot_product_list();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == CHILD){
            View view = View.inflate(mContext, R.layout.item_type_child,null);
            return new ChileViewHolder(view);
        }else if (viewType == HOT){
            View view = View.inflate(mContext, R.layout.item_type_hot,null);
            return new HotViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (result !=null&&result.size()>0){

            if (getItemViewType(position) == CHILD){
                ChileViewHolder holder1 = (ChileViewHolder) holder;
                holder1.gd_type.setAdapter(new TypeChildAdapter(mContext,childList));
                holder1.tv_child.setText("常用分类");
                if (url.toString() == Constants.SKIRT_URL){
                    holder1.iv_top.setVisibility(View.VISIBLE);
                }

            }else if (getItemViewType(position) == HOT){
                HotViewHolder holder2 = (HotViewHolder) holder;
                holder2.gd_type_hot.setAdapter(new TypeHotAdapter(mContext,hot_product_list));
                holder2.tv_hot.setText("热门商品");
            }
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == CHILD){
            CURRENT = CHILD;
        }else if (position == HOT){
            CURRENT = HOT;
        }
        return CURRENT;
    }


    class ChileViewHolder extends RecyclerView.ViewHolder{

        public GridView gd_type;
        private TextView tv_child;
        private ImageView iv_top;
        public ChileViewHolder(View itemView) {
            super(itemView);
            gd_type = (GridView) itemView.findViewById(R.id.gd_type);
            tv_child = (TextView) itemView.findViewById(R.id.tv_hot);
            iv_top = (ImageView) itemView.findViewById(R.id.iv_top);
        }
    }

    class HotViewHolder extends RecyclerView.ViewHolder{

        public GridView gd_type_hot;
        private TextView tv_hot;
        public HotViewHolder(View itemView) {
            super(itemView);
            gd_type_hot = (GridView) itemView.findViewById(R.id.gd_type);
            tv_hot = (TextView) itemView.findViewById(R.id.tv_hot);
        }
    }
}
