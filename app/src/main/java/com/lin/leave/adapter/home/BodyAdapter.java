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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lin.leave.R;
import com.lin.leave.activity.VideoActivity;
import com.lin.leave.bean.HomeBean;
import com.lin.leave.utils.Constants;

import java.util.List;

/**
 * Created by Administrator on 2017/4/29.
 */

public class BodyAdapter extends BaseAdapter {

    Context mContext;
    /** popWindow 关闭按钮 */
    private TextView pop_close;
    private List<HomeBean.ResultBean.HotInfoBean> resultBean;
    private PopupWindow popupWindow;
    public BodyAdapter(Context mContext, List<HomeBean.ResultBean.HotInfoBean> resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        initPopWindow();
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

        final HomeBean.ResultBean.HotInfoBean hotInfoBean = resultBean.get(i);
        holder.tv_body_title.setText(hotInfoBean.getName());
        holder.tv_body_left.setText(hotInfoBean.getProduct_id());
        holder.tv_body_center.setText(hotInfoBean.getCover_price());
        Glide.with(mContext)
                .load(Constants.BASE_URl_IMAGE +hotInfoBean.getFigure())
                .into(holder.iv_body_img);

        holder.iv_body_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop(v);
            }
        });
        holder.gridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVideoActivity();
            }
        });
        return convertView;
    }

    private void startVideoActivity() {
        Intent intent = new Intent(mContext, VideoActivity.class);
        mContext.startActivity(intent);
    }

    class ViewHolder{

        View gridView;
        public ImageView iv_body_img;
        public TextView tv_body_title;
        public TextView tv_body_left;
        public TextView tv_body_center;
        public ImageView iv_body_more;
        public ViewHolder(View view){
            gridView = view;
            iv_body_img = (ImageView) view.findViewById(R.id.iv_body_img);
            iv_body_more = (ImageView) view.findViewById(R.id.iv_body_more);
            tv_body_title = (TextView) view.findViewById(R.id.tv_body_title);
            tv_body_left = (TextView) view.findViewById(R.id.tv_body_left);
            tv_body_center = (TextView) view.findViewById(R.id.tv_body_center);
        }
    }

    /**
     * 初始化弹出的pop
     * */
    private void initPopWindow() {
        View popView = View.inflate(mContext,R.layout.layout_popwindow,null);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        //设置popwindow出现和消失动画
        popupWindow.setAnimationStyle(R.style.PopMenuAnimation);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
            }
        });
        pop_close = (TextView) popView.findViewById(R.id.tv_fir);
    }

    /**
     * 显示popWindow
     * */
    public void showPop(View parent) {
        //设置popwindow显示位置
        popupWindow.showAtLocation(parent,
                Gravity.BOTTOM, 0, 0);

        //设置popwindow如果点击外面区域，便关闭。
        popupWindow.setOutsideTouchable(true);
        pop_close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                popupWindow.dismiss();
            }
        });
    }
}
