package com.edu_app.controller.student.course;

import android.app.Activity;
import android.text.TextUtils;

import com.edu_app.vediochat.WebRTCManager;
import com.edu_app.vediochat.bean.MediaType;
import com.edu_app.vediochat.bean.MyIceServer;
import com.edu_app.vediochat.ui.ChatRoomActivity;
import com.edu_app.vediochat.ui.ChatSingleActivity;
import com.edu_app.vediochat.ws.IConnectEvent;



public class WebrtcUtil {


    public static final String HOST = "47.93.186.97";

    // turn and stun
    private static MyIceServer[] iceServers = {
            new MyIceServer("stun:stun.l.google.com:19302"),

            // 测试地址1
            new MyIceServer("stun:" + HOST + ":3478?transport=udp"),
            new MyIceServer("turn:" + HOST + ":3478?transport=udp",
                    "ddssingsong",
                    "123456"),
            new MyIceServer("turn:" + HOST + ":3478?transport=tcp",
                    "ddssingsong",
                    "123456"),
    };

    // signalling
    private static String WSS = "wss://" + HOST + "/wss";
    //本地测试信令地址
    // private static String WSS = "ws://192.168.1.138:3000";

    // one to one
    public static void callSingle(Activity activity, String wss, String roomId, boolean videoEnable,String uuid) {
        if (TextUtils.isEmpty(wss)) {
            wss = WSS;
        }
        WebRTCManager.getInstance().init(wss, iceServers, new IConnectEvent() {
            @Override
            public void onSuccess() {
                ChatSingleActivity.openActivity(activity, videoEnable,uuid);
            }

            @Override
            public void onFailed(String msg) {

            }
        });
        WebRTCManager.getInstance().connect(videoEnable ? MediaType.TYPE_VIDEO : MediaType.TYPE_AUDIO, roomId,uuid);
    }

    // Videoconferencing
    public static void call(Activity activity, String wss, String roomId,String uuid) {
        if (TextUtils.isEmpty(wss)) {
            wss = WSS;
        }
        WebRTCManager.getInstance().init(wss, iceServers, new IConnectEvent() {
            @Override
            public void onSuccess() {
                ChatRoomActivity.openActivity(activity);
            }

            @Override
            public void onFailed(String msg) {

            }
        });
        WebRTCManager.getInstance().connect(MediaType.TYPE_MEETING, roomId,uuid);
    }


}
