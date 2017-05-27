package com.lin.leave.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lin.leave.R;

/**
 * Created by Lin on 2017/5/5.
 * 自定义加减
 */

class NumberAddSubView extends LinearLayout implements View.OnClickListener {

    private ImageView btn_sub;
    private ImageView btn_add;
    private TextView tv_count;
    private int value = 1;
    private int minValue = 1;
    private int maxValue = 10;

    public NumberAddSubView(Context context) {
        this(context,null);
    }

    public NumberAddSubView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NumberAddSubView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View.inflate(context, R.layout.number_add_sub_layout,this);
        btn_sub = (ImageView) findViewById(R.id.btn_sub);
        btn_add = (ImageView) findViewById(R.id.btn_add);
        tv_count = (TextView) findViewById(R.id.tv_count);
        //设置点击事件
        btn_add.setOnClickListener(this);
        btn_sub.setOnClickListener(this);
        getValue();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                addNumber();
                break;
            case R.id.btn_sub:
                subNumber();
                break;
        }
    }

    private void subNumber() {
        if (value > minValue){
            value = value-1;
        }
        setValue(value);
    }

    private void addNumber() {
        if (value < maxValue){
            value = value+1;
        }
        setValue(value);
    }


    public int getValue() {
        //从文本获取值
        String countStr = tv_count.getText().toString().trim();
        if (countStr!=null){
            value = Integer.parseInt(countStr);
        }
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_count.setText(String.valueOf(value));
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
}
