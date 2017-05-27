package com.lin.leave.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lin.leave.R;
import com.lin.leave.base.BaseActivity;
import com.lin.leave.utils.TimeUtils;
import com.lin.leave.view.MyVideoView;

import java.io.File;
import java.util.IllegalFormatCodePointException;

/**
 * Created by Lin on 2017/5/11.
 */

public class SystemVideoActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "SystemVideoActivity";
    private static boolean SHOW_CONTROL = true;
    private MyVideoView videoView;
    private String fileName;
    private String fileSize;
    private String filePath;
    private RelativeLayout rl_MediaControl;
    private GestureDetector gestureDetector;//手势识别器
    private Button btnVideoStartPause;
    private TextView tv_name;
    private TextView tv_current_time;
    private TextView tv_duration;
    private TimeUtils timeUtils;
    private SeekBar seekbar_video;

    private final static int PROGRESS = 0;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case PROGRESS:
                    int currentPosition = videoView.getCurrentPosition();
                    seekbar_video.setProgress(currentPosition);
                    //更新文本播放进度
                    tv_current_time.setText(timeUtils.stringForTime(currentPosition));
                    handler.removeMessages(PROGRESS);
                    handler.sendEmptyMessageDelayed(PROGRESS,1000);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_video);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getData();
        initData();
        setVideoListener();
    }


    private void getData() {
        Bundle bundle = getIntent().getExtras();
        fileName = bundle.getString("fileName");
        fileSize = bundle.getString("fileSize");
        filePath = bundle.getString("filePath");

        videoView = (MyVideoView ) findViewById(R.id.videoview);
        rl_MediaControl = (RelativeLayout ) findViewById(R.id.media_controller);
        btnVideoStartPause = (Button) findViewById(R.id.btn_video_start_pause);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_current_time = (TextView) findViewById(R.id.tv_current_time);
        tv_duration = (TextView) findViewById(R.id.tv_duration);
        seekbar_video = (SeekBar) findViewById(R.id.seekbar_video);
    }

    private void initData() {
        timeUtils = new TimeUtils();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                startAndpause();
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (SHOW_CONTROL){
                    hideControl();
                }else {
                    showControl();
                }
                return super.onSingleTapConfirmed(e);
            }
        });
        }
        videoView.setVideoPath("/mnt/sdcard/Download/" + fileName);
    }

    private void hideControl() {
        rl_MediaControl.setVisibility(View.GONE);
        SHOW_CONTROL = false;
    }

    private void showControl() {
        rl_MediaControl.setVisibility(View.VISIBLE);
        SHOW_CONTROL = true;
    }

    private void setVideoListener() {
        //视频准备监听
        videoView.setOnPreparedListener(new MyOnPreparedListener());
        //视频出错
        videoView.setOnErrorListener(new MyOnErrorListener());
        //视频完成
        videoView.setOnCompletionListener(new MyOnCompletionListener());

        seekbar_video.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    videoView.seekTo(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_video_start_pause:
                startAndpause();
                break;
        }
    }

    class MyOnPreparedListener implements MediaPlayer.OnPreparedListener{

        @Override
        public void onPrepared(MediaPlayer mp) {
            videoView.start();
            hideControl();
            tv_name.setText(fileName);
            tv_duration.setText(timeUtils.stringForTime(videoView.getDuration()));
            seekbar_video.setMax(videoView.getDuration());
            handler.sendEmptyMessage(PROGRESS);
        }
    }


    class MyOnErrorListener implements MediaPlayer.OnErrorListener{

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {

            Log.e("Tag---","视频播放错误");
            return false;
        }
    }
    class MyOnCompletionListener implements MediaPlayer.OnCompletionListener{
        @Override
        public void onCompletion(MediaPlayer mp) {
            Log.e("Tag---","视频播放完成");
        }
    }

    /**
     * 开始和暂停
     */
    private void startAndpause() {
        if(videoView.isPlaying()){
            //视频在播放-设置暂停
            videoView.pause();
            //按钮状态设置播放
            btnVideoStartPause.setBackgroundResource(R.drawable.btn_video_start_selector);
        }else{
            //视频播放
            videoView.start();
            //按钮状态设置暂停
            btnVideoStartPause.setBackgroundResource(R.drawable.btn_video_pause_selector);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
