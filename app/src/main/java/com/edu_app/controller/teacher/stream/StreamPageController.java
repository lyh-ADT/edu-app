package com.edu_app.controller.teacher.stream;

import android.app.ActionBar;
import android.app.Activity;
import android.view.View;

import android.app.Fragment;

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
}
