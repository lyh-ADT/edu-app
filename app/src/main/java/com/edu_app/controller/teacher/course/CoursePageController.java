package com.edu_app.controller.teacher.course;

import android.view.View;
import android.widget.Button;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.teacher.TeacherInfo;
import com.edu_app.model.teacher.course.CoursePage;

public class CoursePageController extends Controller {
    private Fragment fragment;
    private TeacherInfo info;

    public CoursePageController(Fragment fragment, View view, TeacherInfo info) {
        super(view, new CoursePage());
        this.fragment = fragment;
        this.info = info;
        bindListener();
    }

    @Override
    protected void bindListener(){
        Button startLiveStream_btn = view.findViewById(R.id.start_live_stream_btn);
        startLiveStream_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = fragment.getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, com.edu_app.view.teacher.Fragment.newInstance("live_stream", info));
                transaction.commit();
            }
        });
    }
}
