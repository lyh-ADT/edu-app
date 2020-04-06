package com.edu_app.vediochat.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.percentlayout.widget.PercentRelativeLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.edu_app.R;
import com.edu_app.model.NetworkUtility;
import com.edu_app.model.student.ChatCourseInfo;
import com.edu_app.model.student.ChatMsg;
import com.edu_app.vediochat.IViewCallback;
import com.edu_app.vediochat.PeerConnectionHelper;
import com.edu_app.vediochat.ProxyVideoSink;
import com.edu_app.vediochat.WebRTCManager;
import com.edu_app.vediochat.controller.ChatAdapter;
import com.edu_app.vediochat.controller.RoomChatController;
import com.edu_app.vediochat.controller.RoomChatFragmentAdapter;
import com.edu_app.vediochat.utils.PermissionUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.webrtc.EglBase;
import org.webrtc.MediaStream;
import org.webrtc.RendererCommon;
import org.webrtc.SurfaceViewRenderer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单聊界面
 * 1. 一对一视频通话
 * 2. 一对一语音通话
 */
public class ChatSingleActivity extends AppCompatActivity implements View.OnClickListener {
    private SurfaceViewRenderer local_view;
    private SurfaceViewRenderer remote_view;
    private ProxyVideoSink localRender;
    private ProxyVideoSink remoteRender;

    private WebRTCManager manager;

    private boolean videoEnable;
    private boolean isSwappedFeeds;

    private EglBase rootEglBase;
    private boolean fragmentVisible;
    private String personName;
    private final static String TAG="ChatSingleActivity";
    private FloatingActionButton fab;


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
    private boolean msgFragmentGone;

    private void initFragment(){
        tablayout = findViewById(R.id.coursePage_roomChat_tab);
        viewpager = findViewById(R.id.coursePage_roomChat_viewpager);
        RoomChatController controller = new RoomChatController(this);
        fragments = controller.setAllPageFragment();
        tab_titles = controller.getTabTitle();
        RoomChatFragmentAdapter adapter = new RoomChatFragmentAdapter(getSupportFragmentManager(),fragments,tab_titles);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
        tablayout.addTab(tablayout.newTab().setText(tab_titles.get(0)));
        tablayout.addTab(tablayout.newTab().setText(tab_titles.get(1)));
        msgFragmentGone = false;

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
            remote_view.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
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
        Log.e(TAG,intent.getStringExtra("uuid"));
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
                    tablayout.setVisibility(View.GONE);
                    viewpager.setVisibility(View.GONE);
                    fragmentVisible=false;
                    return true;

                }else {
                    findViewById(R.id.single_switch_mute).setVisibility(View.VISIBLE);
                    findViewById(R.id.single_switch_hang_up).setVisibility(View.VISIBLE);
                    findViewById(R.id.single_switch_camera).setVisibility(View.VISIBLE);
                    tablayout.setVisibility(View.VISIBLE);
                    viewpager.setVisibility(View.VISIBLE);
                    fragmentVisible = true;
                    return true;

                }

        }
        return false;
    }

    /**************刷新界面的消息***************/
    private EditText _editMsg;
    private TextView _tvTeacher;
    private TextView _tvStartTime;
    private TextView _tvTitle;
    private List<ChatMsg> _msgList;
    private ChatAdapter _adapter;
    private RecyclerView _recycler;
    public String getPersonName() {

        Intent intent = getIntent();
        String uuid = intent.getStringExtra("uuid");
        try {
            String body = String.format("{\"stuUid\":\"%s\"}", uuid);
            String response = NetworkUtility.postRequest("http://123.57.101.238:8081/stuGetInfo", body);
            JSONObject jsonObject = JSONObject.parseObject(response);
            Boolean getSuccess = jsonObject.getBoolean("success");
            JSONObject data = jsonObject.getJSONObject("data");
            String userName = data.getString("StuName");
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
            map.put("content",_editMsg.getText().toString());
            Map<String,Object> map2 = new HashMap<String,Object>();
            map2.put("type","__msg");
            map2.put("data",map);
            Log.e("error","发送的信息："+JSONObject.toJSONString(map2));
            manager.sendMsg(JSONObject.toJSONString(map2));
            this._editMsg.setText("");

        }
    }
    public void setEditMsg(EditText ed) {
        this._editMsg = ed;
    }


    public void setActivity(){
        manager.setActivity(this);
        _msgList = new ArrayList<ChatMsg>();
        _recycler = findViewById(R.id.coursePage_course_chat_msgRecycler);
        _adapter = new ChatAdapter(this, _msgList);
        _recycler.setLayoutManager((new WrapContentLinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)));
        _recycler.setAdapter(_adapter);
        _recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }
    public void updateViewMsg(ChatMsg msgObj){
        try{
            int pos = _msgList.size();
            _msgList.add(pos,msgObj);
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    _adapter.notifyItemRangeInserted(pos,_msgList.size()-pos);
                }
            });
        }catch (Exception e){
           e.printStackTrace();
        }


    }
    public void updateViewInfo(ChatCourseInfo infoObj){
        try {
            this._tvTeacher.setText(infoObj.getTeacherName());
            this._tvTitle.setText(infoObj.getCourseName());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Log.e(TAG,sdf.format(infoObj.getStartTimeStamp()));
            this._tvStartTime.setText(sdf.format(infoObj.getStartTimeStamp()));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setCourseInfoView(TextView tvTeacher,TextView tvTitle,TextView tvStartTime) {
        this._tvTeacher = tvTeacher;
        this._tvTitle = tvTitle;
        this._tvStartTime =tvStartTime;
    }
    public class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            // 为了修复刷新消息时系统崩溃
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.e(TAG, "meet a IOOBE in RecyclerView");
            }
        }
    }
}
