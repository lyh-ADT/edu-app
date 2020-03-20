package com.edu_app.vediochat.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu_app.R;
import com.edu_app.controller.student.course.CourserFragmentAdapter;
import com.edu_app.model.NetworkUtility;
import com.edu_app.vediochat.IViewCallback;
import com.edu_app.vediochat.PeerConnectionHelper;
import com.edu_app.vediochat.ProxyVideoSink;
import com.edu_app.vediochat.WebRTCManager;
import com.edu_app.vediochat.controller.RoomChatController;
import com.edu_app.vediochat.controller.RoomChatFragmentAdapter;
import com.edu_app.vediochat.utils.PermissionUtil;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;
import org.webrtc.EglBase;
import org.webrtc.MediaStream;
import org.webrtc.RendererCommon;
import org.webrtc.SurfaceViewRenderer;

import java.io.IOException;
import java.nio.file.Watchable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 单聊界面
 * 1. 一对一视频通话
 * 2. 一对一语音通话
 */
public class ChatSingleActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private SurfaceViewRenderer local_view;
    private SurfaceViewRenderer remote_view;
    private ProxyVideoSink localRender;
    private ProxyVideoSink remoteRender;

    private WebRTCManager manager;

    private boolean videoEnable;
    private boolean isSwappedFeeds;

    private EglBase rootEglBase;
    private boolean fragmentVisible;
    private String message=null;
    private String personName;

    public static void openActivity(Activity activity, boolean videoEnable,String uuid) {
        Intent intent = new Intent(activity, ChatSingleActivity.class);
        intent.putExtra("videoEnable", videoEnable);
        intent.putExtra("uuid",uuid);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_single);
        initVar();
        initFragment();
        initListener();
    }


    private TabLayout tablayout;
    private ViewPager viewpager;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> tab_titles;
    private void initFragment(){
        tablayout = findViewById(R.id.coursePage_roomChat_tab);
        viewpager = findViewById(R.id.coursePage_roomChat_viewpager);
        RoomChatController controller = new RoomChatController(this);
        fragments = controller.setAllPageFragment();
        tab_titles = controller.getTabTitle();
        RoomChatFragmentAdapter adapter = new RoomChatFragmentAdapter(getSupportFragmentManager(),fragments,tab_titles);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);


    }


    private int previewX, previewY;
    private int moveX, moveY;

    private void initVar() {
        Intent intent = getIntent();
        videoEnable = intent.getBooleanExtra("videoEnable", false);

        ChatSingleFragment chatSingleFragment = new ChatSingleFragment();
        replaceFragment(chatSingleFragment, videoEnable);
        fragmentVisible = true;
        rootEglBase = EglBase.create();
        if (videoEnable) {
            local_view = findViewById(R.id.local_view_render);
            remote_view = findViewById(R.id.remote_view_render);

            // 本地图像初始化
            local_view.init(rootEglBase.getEglBaseContext(), null);
            local_view.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
            local_view.setZOrderMediaOverlay(true);
            local_view.setMirror(true);
            localRender = new ProxyVideoSink();
            //远端图像初始化
            remote_view.init(rootEglBase.getEglBaseContext(), null);
            remote_view.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_BALANCED);
            remote_view.setMirror(false);
            remoteRender = new ProxyVideoSink();
            setSwappedFeeds(true);

            local_view.setOnClickListener(v -> setSwappedFeeds(!isSwappedFeeds));
        }

        startCall();

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        if (videoEnable) {
            // 设置小视频可以移动
            local_view.setOnTouchListener((view, motionEvent) -> {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        previewX = (int) motionEvent.getX();
                        previewY = (int) motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x = (int) motionEvent.getX();
                        int y = (int) motionEvent.getY();
                        moveX = (int) motionEvent.getX();
                        moveY = (int) motionEvent.getY();
                        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) local_view.getLayoutParams();
                        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0); // Clears the rule, as there is no removeRule until API 17.
                        lp.addRule(RelativeLayout.ALIGN_PARENT_END, 0);
                        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                        lp.addRule(RelativeLayout.ALIGN_PARENT_START, 0);
                        int left = lp.leftMargin + (x - previewX);
                        int top = lp.topMargin + (y - previewY);
                        lp.leftMargin = left;
                        lp.topMargin = top;
                        view.setLayoutParams(lp);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (moveX == 0 && moveY == 0) {
                            view.performClick();
                        }
                        moveX = 0;
                        moveY = 0;
                        break;
                }
                return true;
            });
        }
    }

    private void setSwappedFeeds(boolean isSwappedFeeds) {
        this.isSwappedFeeds = isSwappedFeeds;
        localRender.setTarget(isSwappedFeeds ? remote_view : local_view);
        remoteRender.setTarget(isSwappedFeeds ? local_view : remote_view);
    }

    private void startCall() {
        manager = WebRTCManager.getInstance();
        manager.setCallback(new IViewCallback() {
//            去掉本地的自己头像
//            @Override
//            public void onSetLocalStream(MediaStream stream, String socketId) {
//                if (stream.videoTracks.size() > 0) {
//                    stream.videoTracks.get(0).addSink(localRender);
//                }
//
//                if (videoEnable) {
//                    stream.videoTracks.get(0).setEnabled(true);
//                }
//            }

            @Override
            public void onAddRemoteStream(MediaStream stream, String socketId) {
                if(socketId.startsWith("DoNotShow")){
                    return;
                }
                if (stream.videoTracks.size() > 0) {
                    stream.videoTracks.get(0).addSink(remoteRender);
                }
                if (videoEnable && !socketId.startsWith("DoNotShow")) {
                    stream.videoTracks.get(0).setEnabled(true);

                    runOnUiThread(() -> setSwappedFeeds(false));
                }
            }

            @Override
            public void onCloseWithId(String socketId) {
                runOnUiThread(() -> {
                    disConnect();
                    ChatSingleActivity.this.finish();
                });

            }
        });
        if (!PermissionUtil.isNeedRequestPermission(ChatSingleActivity.this)) {
            manager.joinRoom(getApplicationContext(), rootEglBase);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                personName = getPersonName();
            }
        }).start();
        Intent intent = getIntent();
        Log.e("error",intent.getStringExtra("uuid"));
        manager.sendMsg(intent.getStringExtra("uuid"));

    }

    private void replaceFragment(Fragment fragment, boolean videoEnable) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("videoEnable", videoEnable);
        fragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.single_container, fragment)
                .commit();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
    }


    // 切换摄像头
    public void switchCamera() {
        manager.switchCamera();
    }

    // 挂断
    public void hangUp() {
        disConnect();
        this.finish();
    }

    // 静音
    public void toggleMic(boolean enable) {
        manager.toggleMute(enable);
    }

    // 扬声器
    public void toggleSpeaker(boolean enable) {
        manager.toggleSpeaker(enable);

    }

    @Override
    protected void onDestroy() {
        disConnect();
        super.onDestroy();

    }

    private void disConnect() {
        manager.exitRoom();
        if (localRender != null) {
            localRender.setTarget(null);
            localRender = null;
        }
        if (remoteRender != null) {
            remoteRender.setTarget(null);
            remoteRender = null;
        }

        if (local_view != null) {
            local_view.release();
            local_view = null;
        }
        if (remote_view != null) {
            remote_view.release();
            remote_view = null;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            Log.i(PeerConnectionHelper.TAG, "[Permission] " + permissions[i] + " is " + (grantResults[i] == PackageManager.PERMISSION_GRANTED ? "granted" : "denied"));
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                finish();
                break;
            }
        }
        manager.joinRoom(getApplicationContext(), rootEglBase);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(fragmentVisible){
                    findViewById(R.id.single_switch_mute).setVisibility(View.GONE);
                    findViewById(R.id.single_switch_hang_up).setVisibility(View.GONE);
                    findViewById(R.id.single_switch_camera).setVisibility(View.GONE);
                    fragmentVisible=false;
                    return true;

                }else {
                    findViewById(R.id.single_switch_mute).setVisibility(View.VISIBLE);
                    findViewById(R.id.single_switch_hang_up).setVisibility(View.VISIBLE);
                    findViewById(R.id.single_switch_camera).setVisibility(View.VISIBLE);
                    fragmentVisible = true;
                    return true;

                }

        }
        return false;
    }
    public String getPersonName() {

        Intent intent = getIntent();
        String uuid = intent.getStringExtra("uuid");
        try {
            String body = String.format("{\"stuUid\":\"%s\"}", uuid);
            Log.e("error", body);
            String response = NetworkUtility.postRequest("http://139.159.176.78:8081/stuGetInfo", body);
            JSONObject jsonObject = JSONObject.parseObject(response);
            Boolean getSuccess = jsonObject.getBoolean("success");
            JSONObject data = jsonObject.getJSONObject("data");
            String userName = data.getString("StuName");
            Log.e("error",data.toString());
            return userName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.coursePage_roomChat_btnMsg){
            Map<String,String> map = new HashMap<String,String>();
            map.put("userId",personName);
            map.put("content",message);
            Map<String,Object> map2 = new HashMap<String,Object>();
            map2.put("type","__msg");
            map2.put("data",map);
            Log.e("error","发送的信息："+JSONObject.toJSONString(map2));
            manager.sendMsg(JSONObject.toJSONString(map2));
        }
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable s) {
        message = s.toString();
    }
    public void setActivity(){
        manager.setActivity(this);
    }
}
