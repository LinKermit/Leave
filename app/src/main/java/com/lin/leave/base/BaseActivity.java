package com.lin.leave.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.lin.leave.R;
import com.lin.leave.themeUtils.CardPickerDialog;
import com.lin.leave.themeUtils.ThemeHelper;

/**
 * Created by Lin on 2017/5/5.
 * activity基类，主体换肤
 */

public abstract class BaseActivity extends AppCompatActivity implements CardPickerDialog.ClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    /**
//     * 初始化view
//     */
//    protected abstract void initView();
//
//    //初始化toolbar
//    protected abstract void setToolBar();

    /**
     * 主体切换
     * @param currentTheme
     */
    @Override
    public void onConfirm(int currentTheme) {

        if (ThemeHelper.getTheme(BaseActivity.this) != currentTheme) {
            ThemeHelper.setTheme(BaseActivity.this, currentTheme);
            ThemeUtils.refreshUI(BaseActivity.this, new ThemeUtils.ExtraRefreshable() {

                        @Override
                        public void refreshGlobal(Activity activity) {

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                final BaseActivity context = BaseActivity.this;
                                ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(
                                        null,
                                        null, ThemeUtils.getThemeAttrColor(context, android.R.attr.colorPrimary));
                                setTaskDescription(taskDescription);
                                getWindow().setStatusBarColor(ThemeUtils.getColorById(context,
                                        R.color.theme_color_primary_dark));
                            }
                        }


                        @Override
                        public void refreshSpecificView(View view) {

                        }
                    }
            );
        }
    }

    /**
     * 状态栏背景
     * @param savedInstanceState
     * @param persistentState
     */
    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {

        super.onPostCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ThemeUtils.getColorById(this, R.color.theme_color_primary_dark));
            ActivityManager.TaskDescription description = new ActivityManager.TaskDescription(null, null,
                    ThemeUtils.getThemeAttrColor(this, android.R.attr.colorPrimary));
            setTaskDescription(description);
        }
    }
}
