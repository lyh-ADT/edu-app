package com.edu_app.controller.teacher.stream;

import android.view.View;

import androidx.fragment.app.Fragment;

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
    }
}
