package com.lin.leave.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lin.leave.R;
import com.lin.leave.activity.VideoActivity;
import com.lin.leave.bean.HomeBean;
import com.lin.leave.bean.TypeBean;
import com.lin.leave.utils.Constants;

import java.util.List;

/**
 * Created by Administrator on 2017/4/29.
 */

public class TypeChildAdapter extends BaseAdapter {

    Context mContext;
    private List<TypeBean.ResultBean.ChildBean> resultBean;
    public TypeChildAdapter(Context mContext, List<TypeBean.ResultBean.ChildBean> resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
    }

    @Override
    public int getCount() {
        return resultBean.size();
    }

    @Override
    public Object getItem(int i) {
        return resultBean.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_body, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final TypeBean.ResultBean.ChildBean childBean = resultBean.get(i);
        holder.tv_body_title.setText(childBean.getName());
        holder.rl_type.setVisibility(View.GONE);
        Glide.with(mContext)
                .load(Constants.BASE_URl_IMAGE +childBean.getPic())
                .into(holder.iv_body_img);
        return convertView;
    }


    class ViewHolder{
        View gridView;
        public ImageView iv_body_img;
        public TextView tv_body_title;
        RelativeLayout rl_type;
        public ViewHolder(View view){
            gridView = view;
            iv_body_img = (ImageView) view.findViewById(R.id.iv_body_img);
            tv_body_title = (TextView) view.findViewById(R.id.tv_body_title);
            rl_type = (RelativeLayout) view.findViewById(R.id.rl_type);
        }
    }

}
