package com.edu_app.controller.teacher.stream;

import android.content.res.Configuration;
import android.util.Log;
import android.view.View;

import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.teacher.TeacherInfo;
import com.edu_app.model.teacher.stream.LiveStreamPage;
import com.edu_app.view.teacher.Fragment;

import cn.nodemedia.NodeCameraView;
import cn.nodemedia.NodePublisher;
import cn.nodemedia.NodePublisherDelegate;

public class StreamPageController extends Controller implements NodePublisherDelegate {
    private Fragment fragment;
    private TeacherInfo info;
    private NodePublisher nodePublisher;
    private LiveStreamPage model;

    public StreamPageController(Fragment fragment, View view, TeacherInfo info) {
        super(view, new LiveStreamPage(info));
        this.fragment = fragment;
        this.info = info;
        this.model = (LiveStreamPage)super.model;
        setFullScreen(fragment.getActivity());
        onConfigurationChanged(fragment.getResources().getConfiguration());
        bindListener();
        nodePublisher = new NodePublisher(fragment.getActivity().getApplicationContext(),"c0KzkWKg5LoyRg+hR+2wtrnf/k61cQuoAibf2T8ghqFObNhHVuBiWqn28RhSSyAmLhcxuLVOXVLUf0Blk/axig==");
        nodePublisher.setNodePublisherDelegate(this);
        nodePublisher.setOutputUrl("rtmp://123.57.101.238:1935/live/test_stream");
        NodeCameraView nodeCameraView = view.findViewById(R.id.video);
        nodePublisher.setCameraPreview(nodeCameraView, 0, true);
        nodePublisher.setVideoParam(1, 15, 500000, 0, false);
        nodePublisher.setKeyFrameInterval(1);
        nodePublisher.setAudioParam(32000, 1, 44100);
        nodePublisher.setDenoiseEnable(true);
        nodePublisher.setHwEnable(true);
        nodePublisher.setBeautyLevel(0);
        nodePublisher.setAutoReconnectWaitTimeout(-1);
        nodePublisher.startPreview();
    }

    @Override
    protected void bindListener() {
        View stop_btn = view.findViewById(R.id.stop_btn);
        stop_btn.setOnClickListener(new View.OnClickListener() {
            private boolean started = false;
            @Override
            public void onClick(View v) {
                TextView textView = (TextView)v;
                if(started){
                    started = false;
                    nodePublisher.stop();
                    textView.setText(R.string.start_text);
                } else {
                    started = true;
                    nodePublisher.start();
                    textView.setText(R.string.stop_text);
                }
            }
        });

        View send_btn = view.findViewById(R.id.send_btn);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input_et = StreamPageController.this.view.findViewById(R.id.input_chat_et);
                model.sendChatMessage(input_et.getText().toString());
                input_et.setText("");
            }
        });

        WebView chat_wv = view.findViewById(R.id.chat_wv);
        chat_wv.loadUrl(model.getChatRoomUrl());

        Button mute_btn = view.findViewById(R.id.mute_btn);
        mute_btn.setOnClickListener(new View.OnClickListener() {
            private boolean isMuted = false;
            @Override
            public void onClick(View v) {
                if(isMuted){
                    isMuted = false;
                    nodePublisher.setAudioEnable(true);
                    ((Button)v).setText(R.string.mute_text);
                } else {
                    isMuted = true;
                    nodePublisher.setAudioEnable(false);
                    ((Button)v).setText(R.string.record_audio_text);
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        // TODO: 重构 用Fragment分开两个区域
        final LinearLayout.LayoutParams PORTRAIT_PARAMS = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(0));
        final LinearLayout.LayoutParams LANDSCAPE_PARAMS = new LinearLayout.LayoutParams(dip2px(0), ViewGroup.LayoutParams.MATCH_PARENT);
        PORTRAIT_PARAMS.weight = 1;
        LANDSCAPE_PARAMS.weight = 1;

        int orientation = config.orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            // 竖屏模式
            LinearLayout page = (LinearLayout)view;
            page.setOrientation(LinearLayout.VERTICAL);

            LinearLayout control = page.findViewById(R.id.stream_control_lo);
            control.setLayoutParams(PORTRAIT_PARAMS);
            control.setOrientation(LinearLayout.HORIZONTAL);

            View video = page.findViewById(R.id.video);
            video.setLayoutParams(LANDSCAPE_PARAMS);

            View chat_lo = page.findViewById(R.id.chat_lo);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(PORTRAIT_PARAMS);
            params.weight = 2;
            chat_lo.setLayoutParams(params);

            LinearLayout button = control.findViewById(R.id.button_lo);
            button.setOrientation(LinearLayout.VERTICAL);

            page.invalidate();
        } else if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            // 横屏模式
            LinearLayout page = (LinearLayout)view;
            page.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout control = page.findViewById(R.id.stream_control_lo);
            control.setLayoutParams(LANDSCAPE_PARAMS);
            control.setOrientation(LinearLayout.VERTICAL);

            View video = page.findViewById(R.id.video);
            video.setLayoutParams(PORTRAIT_PARAMS);

            View chat_lo = page.findViewById(R.id.chat_lo);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LANDSCAPE_PARAMS);
            params.weight = 2;
            chat_lo.setLayoutParams(params);

            LinearLayout button = control.findViewById(R.id.button_lo);
            button.setOrientation(LinearLayout.HORIZONTAL);

            page.invalidate();
        }
    }

    @Override
    public void onEventCallback(NodePublisher streamer, int event, String msg) {
        Log.i("ADT", msg);
    }
}
