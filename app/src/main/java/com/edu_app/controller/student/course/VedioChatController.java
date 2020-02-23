package com.edu_app.controller.student.course;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;

import java.net.URISyntaxException;
import java.util.List;

public class VedioChatController {
    private final AppCompatActivity activity;
    private PeerConnectionFactory pcFactory;
    private List<PeerConnection.IceServer> iceServers;
    private PeerConnection pc;
    private MyPeerConnectionManager manager;
    private String roomId;
    private MySocket websocket;


    public VedioChatController(AppCompatActivity activity) {
        this.activity = activity;
        initVar();
    }

    // 初始化变量
    private void initVar() {
        roomId = this.activity.getIntent().getStringExtra("roomId");
        manager = new MyPeerConnectionManager((Context) this.activity);
        pcFactory = manager.getPeerConnectionFactory();
        pc = manager.getPeerConnection(pcFactory);
        try {
            websocket = manager.setMyWebSocket("www.baidu.com",500);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        websocket.setPeerConnection(pc);
    }

    // 建立连接，加入房间
    public void startCall() {


    }

    //    设置建立连接后的视图
    public void setChatView() {

    }

    //    控制功能碎片的显示和隐藏
    public void controlMenuFragment() {

    }

    //  挂断
    public void hangUp() {
    }

    //  切换摄像头
    public void switchCamera() {
    }

    //  打开扬声器
    public void openSpeaker() {

    }

    //  关闭扬声器
    public void closeSpeaker() {

    }

    //   打开麦克风
    public void openMicrophone() {

    }

    //   关闭麦克风
    public void closeMicrophone() {

    }
}
