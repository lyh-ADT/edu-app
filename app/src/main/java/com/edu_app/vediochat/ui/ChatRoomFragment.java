package com.edu_app.vediochat.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.edu_app.R;
import com.edu_app.vediochat.utils.Utils;

/**
 * 视频会议控制界面
*
 */
public class ChatRoomFragment extends Fragment {

    public View rootView;
    private TextView switch_mute;
    private TextView switch_hang_up;
    private TextView switch_camera;
    private TextView hand_free;
    private TextView open_camera;
    private ChatRoomActivity activity;

    private boolean enableMic = true;
    private boolean enableSpeaker = true;
    private boolean enableCamera = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ChatRoomActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = onInitloadView(inflater, container, savedInstanceState);
            initView(rootView);
            initListener();
        }
        return rootView;
    }


    private View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_room_control, container, false);
    }

    private void initView(View rootView) {
        switch_mute = rootView.findViewById(R.id.mult_switch_mute);
        switch_hang_up = rootView.findViewById(R.id.mult_switch_hang_up);
        switch_camera = rootView.findViewById(R.id.mult_switch_camera);
        hand_free = rootView.findViewById(R.id.mult_hand_free);
        open_camera = rootView.findViewById(R.id.mult_open_camera);

    }

    private void initListener() {
        switch_mute.setOnClickListener(v -> {
            enableMic = !enableMic;
            toggleMic(enableMic);
            activity.toggleMic(enableMic);

        });
        switch_hang_up.setOnClickListener(v -> activity.hangUp());
        switch_camera.setOnClickListener(v -> activity.switchCamera());
        hand_free.setOnClickListener(v -> {
            enableSpeaker = !enableSpeaker;
            toggleSpeaker(enableSpeaker);
            activity.toggleSpeaker(enableSpeaker);
        });
        open_camera.setOnClickListener(v -> {
            enableCamera = !enableCamera;
            toggleOpenCamera(enableCamera);
            activity.toggleCamera(enableCamera);
        });
    }

    private void toggleMic(boolean isMicEnable) {
        if (isMicEnable) {
            Drawable drawable = ContextCompat.getDrawable(activity, R.drawable.mute_default);
            if (drawable != null) {
                drawable.setBounds(0, 0, Utils.dip2px(activity, 60), Utils.dip2px(activity, 60));
            }
            switch_mute.setCompoundDrawables(null, drawable, null, null);
        } else {
            Drawable drawable = ContextCompat.getDrawable(activity, R.drawable.mute);
            if (drawable != null) {
                drawable.setBounds(0, 0, Utils.dip2px(activity, 60), Utils.dip2px(activity, 60));
            }
            switch_mute.setCompoundDrawables(null, drawable, null, null);
        }
    }

    public void toggleSpeaker(boolean enableSpeaker) {
        if (enableSpeaker) {
            Drawable drawable = ContextCompat.getDrawable(activity, R.drawable.hands_free);
            if (drawable != null) {
                drawable.setBounds(0, 0, Utils.dip2px(activity, 60), Utils.dip2px(activity, 60));
            }
            hand_free.setCompoundDrawables(null, drawable, null, null);
        } else {
            Drawable drawable = ContextCompat.getDrawable(activity, R.drawable.hands_free_default);
            if (drawable != null) {
                drawable.setBounds(0, 0, Utils.dip2px(activity, 60), Utils.dip2px(activity, 60));
            }
            hand_free.setCompoundDrawables(null, drawable, null, null);
        }
    }

    private void toggleOpenCamera(boolean enableCamera) {
        if (enableCamera) {
            Drawable drawable = ContextCompat.getDrawable(activity, R.drawable.open_camera_normal);
            if (drawable != null) {
                drawable.setBounds(0, 0, Utils.dip2px(activity, 60), Utils.dip2px(activity, 60));
            }
            open_camera.setCompoundDrawables(null, drawable, null, null);
            open_camera.setText(R.string.close_camera);
            switch_camera.setVisibility(View.VISIBLE);
        } else {
            Drawable drawable = ContextCompat.getDrawable(activity, R.drawable.open_camera_press);
            if (drawable != null) {
                drawable.setBounds(0, 0, Utils.dip2px(activity, 60), Utils.dip2px(activity, 60));
            }
            open_camera.setCompoundDrawables(null, drawable, null, null);
            open_camera.setText(R.string.open_camera);
            switch_camera.setVisibility(View.GONE);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (rootView != null) {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }
    }


}
