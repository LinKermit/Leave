package com.lin.leave.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lin.leave.R;

/**
 * Created by Lin on 2017/5/8.
 */

public class CustomEmptyView extends FrameLayout {
    private ImageView mEmptyImg;

    private TextView mEmptyText;
    public CustomEmptyView(@NonNull Context context) {
        this(context,null);
    }

    public CustomEmptyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomEmptyView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty, this);
        View view = View.inflate(getContext(),R.layout.layout_empty,this);//必须传this
        mEmptyImg = (ImageView) view.findViewById(R.id.empty_img);
        mEmptyText = (TextView) view.findViewById(R.id.empty_text);

    }

    public void setmEmptyImg(int sourse) {
        mEmptyImg.setImageResource(sourse);
    }

    public void setmEmptyText(String mes) {
        mEmptyText.setText(mes);
    }
}
