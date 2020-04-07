package com.edu_app.controller.student.course;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.edu_app.controller.student.Controller;
import com.edu_app.R;

import cn.nodemedia.NodePlayer;
import cn.nodemedia.NodePlayerView;

/**
 * 用于控制直播活动页面
 */
public class CourseLiveController extends Controller implements View.OnClickListener {
    private final AppCompatActivity activity;
    private boolean fullFlag;
    private NodePlayerView playerview;
    private NodePlayer player;
    private TextView tvFull;
    private int orientation;
    private TextView tvStop;

    public CourseLiveController(AppCompatActivity activity) {
        this.activity = activity;
        this.initView();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.coursePage_live_tv_fullScreen:
                orientation = this.activity.getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

                    this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {

                    this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                break;
            case R.id.coursePage_live_tv_stop:
                if(player.isPlaying()){
                    player.setVideoEnable(false);
                    player.setAudioEnable(false);
                    player.stop();
                }else {
                    player.setVideoEnable(true);
                    player.setAudioEnable(true);
                    player.start();

                }

        }


    }

    //    进入直播页面开始观看直播
    private void initView() {
        this.tvFull = (TextView) this.activity.findViewById(R.id.coursePage_live_tv_fullScreen);
        this.tvFull.setOnClickListener(this);
        this.tvStop = (TextView) this.activity.findViewById(R.id.coursePage_live_tv_stop);
        this.tvStop.setOnClickListener(this);
        playerview = activity.findViewById(R.id.coursePage_live_playerView);

        player = new NodePlayer(activity.getBaseContext());
        player.setPlayerView(playerview);
        player.setAudioEnable(true);
        player.setVideoEnable(true);
//        硬件加速
        player.setHWEnable(true);
//        设置启动缓冲时间
        player.setBufferTime(2000);
//        设置协议
        player.setRtspTransport(NodePlayer.RTSP_TRANSPORT_UDP_MULTICAST);
        player.setInputUrl("rtmp://123.57.101.238:1935/live/test_stream");
        player.start();
    }
    public NodePlayer getPlayer(){
        if(player!=null){
            return player;
        }
        return null;
    }
}
