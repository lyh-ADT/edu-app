package com.edu_app.controller.teacher.stream;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import android.app.Fragment;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.teacher.TeacherInfo;
import com.edu_app.model.teacher.stream.LiveStreamPage;

import java.io.IOException;

public class StreamPageController extends Controller {
    private Fragment fragment;
    private TeacherInfo info;
    private Camera camera;

    public StreamPageController(Fragment fragment, View view, TeacherInfo info) {
        super(view, new LiveStreamPage());
        this.fragment = fragment;
        this.info = info;
        setFullScreen();
        onConfigurationChanged(fragment.getResources().getConfiguration());
        bindListener();
    }

    @Override
    protected void bindListener() {
        View stop_btn = view.findViewById(R.id.stop_btn);
        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 停止直播的逻辑
            }
        });

        View send_btn = view.findViewById(R.id.send_btn);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input_et = StreamPageController.this.view.findViewById(R.id.input_chat_et);
                input_et.getText();
                // TODO: 发送弹幕的网络逻辑
            }
        });

        SurfaceView video_sv = view.findViewById(R.id.video);
        video_sv.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                camera = Camera.open();
                try {
                    camera.setPreviewDisplay(holder);
                    setCameraDisplayOrientation(fragment.getActivity(), 0, camera);
                    camera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    private void setFullScreen(){
        Activity activity = fragment.getActivity();
        // 关闭标题栏
        ActionBar actionBar = activity.getActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        // 关闭底部的导航栏
        View navigation_bar = activity.findViewById(R.id.navigation_bar);
        navigation_bar.setVisibility(View.GONE);
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
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

            page.invalidate();
        }
        if(camera != null){
            setCameraDisplayOrientation(fragment.getActivity(), 0, camera);
        }
    }

    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }
}
