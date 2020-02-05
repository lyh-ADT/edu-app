package com.edu_app.controller.teacher.stream;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Configuration;
import android.view.View;

import android.app.Fragment;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.teacher.TeacherInfo;
import com.edu_app.model.teacher.stream.LiveStreamPage;

public class StreamPageController extends Controller {
    private Fragment fragment;
    private TeacherInfo info;

    public StreamPageController(Fragment fragment, View view, TeacherInfo info) {
        super(view, new LiveStreamPage());
        this.fragment = fragment;
        this.info = info;
        setFullScreen();
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
        final LinearLayout.LayoutParams PORTRAIT_PARAMS = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(200));
        final LinearLayout.LayoutParams LANDSCAPE_PARAMS = new LinearLayout.LayoutParams(dip2px(200), ViewGroup.LayoutParams.MATCH_PARENT);

        int orientation = config.orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            // 竖屏模式
            LinearLayout page = (LinearLayout)view;
            page.setOrientation(LinearLayout.VERTICAL);
            page.setLayoutParams(new FrameLayout.LayoutParams(PORTRAIT_PARAMS));

            LinearLayout control = page.findViewById(R.id.stream_control_lo);
            control.setOrientation(LinearLayout.HORIZONTAL);

            VideoView video = page.findViewById(R.id.video);
            video.setLayoutParams(LANDSCAPE_PARAMS);

            TextView chat_tv = page.findViewById(R.id.chat_tv);
            chat_tv.setText("竖");

            page.invalidate();
        } else if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            // 横屏模式
            LinearLayout page = (LinearLayout)view;
            page.setOrientation(LinearLayout.HORIZONTAL);
            page.setLayoutParams(new FrameLayout.LayoutParams(LANDSCAPE_PARAMS));

            LinearLayout control = page.findViewById(R.id.stream_control_lo);
            control.setOrientation(LinearLayout.VERTICAL);

            VideoView video = page.findViewById(R.id.video);
            video.setLayoutParams(PORTRAIT_PARAMS);

            TextView chat_tv = page.findViewById(R.id.chat_tv);
            chat_tv.setText("横");

            page.invalidate();
        }
    }
}
