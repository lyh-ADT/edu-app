package com.edu_app.controller.student.course;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.edu_app.R;

import cn.nodemedia.NodePlayer;
import cn.nodemedia.NodePlayerView;

/**
 * 用于控制直播活动页面
 */
public class CourseLiveController implements View.OnClickListener {
    private final AppCompatActivity activity;
    private NodePlayerView playerview;
    private NodePlayer player;
    public CourseLiveController(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        playerview = activity.findViewById(R.id.coursePage_live_playerView);

        player = new NodePlayer(activity.getBaseContext());
        player.setPlayerView(playerview);
        player.setAudioEnable(true);
        player.setVideoEnable(true);
//        硬件加速
        player.setHWEnable(true);
//        设置启动缓冲时间
//        player.setBufferTime();
//        设置协议
        player.setRtspTransport(NodePlayer.RTSP_TRANSPORT_HTTP);
        player.setInputUrl("http://www.huya.com/19739140");
        player.start();
    }
}
