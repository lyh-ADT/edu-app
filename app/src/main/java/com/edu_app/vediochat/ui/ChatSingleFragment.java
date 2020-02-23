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
 * 单聊控制界面
 * Created by dds on 2019/1/7.
 * android_shuai@163.com
 */
public class ChatSingleFragment extends Fragment {

    public View rootView;
    private TextView single_switch_mute;
    private TextView single_switch_hang_up;
    private TextView single_switch_camera;
    private TextView single_hand_free;
    private boolean enableMic = true;
    private boolean enableSpeaker = false;
    private boolean videoEnable;
    private ChatSingleActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ChatSingleActivity) getActivity();
        Bundle bundle = getArguments();
        if (bundle != null) {
            videoEnable = bundle.getBoolean("videoEnable");
        }
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
        return inflater.inflate(R.layout.fragment_room_control_single, container, false);
    }

    private void initView(View rootView) {
        single_switch_mute = rootView.findViewById(R.id.single_switch_mute);
        single_switch_hang_up = rootView.findViewById(R.id.single_switch_hang_up);
        single_switch_camera = rootView.findViewById(R.id.single_switch_camera);
        single_hand_free = rootView.findViewById(R.id.single_hand_free);
        if (videoEnable) {
            single_hand_free.setVisibility(View.GONE);
            single_switch_camera.setVisibility(View.VISIBLE);
        } else {
            single_hand_free.setVisibility(View.VISIBLE);
            single_switch_camera.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        single_switch_mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableMic = !enableMic;
                if (enableMic) {
                    Drawable drawable = ContextCompat.getDrawable(activity, R.drawable.mute_default);
                    if (drawable != null) {
                        drawable.setBounds(0, 0, Utils.dip2px(activity, 60), Utils.dip2px(activity, 60));
                    }
                    single_switch_mute.setCompoundDrawables(null, drawable, null, null);
                } else {
                    Drawable drawable = ContextCompat.getDrawable(activity, R.drawable.mute);
                    if (drawable != null) {
                        drawable.setBounds(0, 0, Utils.dip2px(activity, 60), Utils.dip2px(activity, 60));
                    }
                    single_switch_mute.setCompoundDrawables(null, drawable, null, null);
                }
                activity.toggleMic(enableMic);

            }
        });
        single_switch_hang_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.hangUp();
            }
        });
        single_switch_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.switchCamera();
            }
        });

        single_hand_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableSpeaker = !enableSpeaker;
                if (enableSpeaker) {
                    Drawable drawable = ContextCompat.getDrawable(activity, R.drawable.hands_free);
                    if (drawable != null) {
                        drawable.setBounds(0, 0, Utils.dip2px(activity, 60), Utils.dip2px(activity, 60));
                    }
                    single_hand_free.setCompoundDrawables(null, drawable, null, null);
                } else {
                    Drawable drawable = ContextCompat.getDrawable(activity, R.drawable.hands_free_default);
                    if (drawable != null) {
                        drawable.setBounds(0, 0, Utils.dip2px(activity, 60), Utils.dip2px(activity, 60));
                    }
                    single_hand_free.setCompoundDrawables(null, drawable, null, null);
                }
                activity.toggleSpeaker(enableSpeaker);
            }
        });
    }

}
