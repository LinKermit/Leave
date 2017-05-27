package com.lin.leave.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.lin.leave.R;


/**
 * Created by Lin on 2017/5/10.
 */

public class NumberProgress extends View {

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private int value = 0;
    private int mWidth;
    private int mHeight;

    private Paint paint;
    public NumberProgress(Context context) {
        this(context,null);
    }

    public NumberProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NumberProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setColor(getResources().getColor(R.color.theme_color_secondary));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();//获取控件的宽高
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        value = getValue();
        canvas.drawRect(0,0,value,mHeight,paint);
    }

}
