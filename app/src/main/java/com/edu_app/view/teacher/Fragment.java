package com.edu_app.view.teacher;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.controller.teacher.course.CoursePageController;
import com.edu_app.controller.teacher.practice.PracticeInfoController;
import com.edu_app.controller.teacher.question.QuestionInfoController;
import com.edu_app.controller.teacher.stream.StreamPageController;
import com.edu_app.model.teacher.TeacherInfo;

/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 * Use the {@link Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment extends androidx.fragment.app.Fragment {
    private static final String FRAGMENT_TYPE = "fragment_type";
    private static final String TEACHER_INFO = "teacher_info";
    private static final String CALLBACK = "callback";

    private String fragmentType;
    private TeacherInfo teacherInfo;
    private Controller controller;
    private Controller.Callback callback;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param fragmentType Parameter 1.
     * @return A new instance of fragment Fragment.
     */
    public static Fragment newInstance(String fragmentType, TeacherInfo teacherInfo) {
        Fragment fragment = new Fragment();
        Bundle args = new Bundle();
        args.putString(FRAGMENT_TYPE, fragmentType);
        args.putSerializable(TEACHER_INFO, teacherInfo);
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance(String fragmentType, TeacherInfo teacherInfo, Controller.Callback callback) {
        Fragment fragment = new Fragment();
        Bundle args = new Bundle();
        args.putString(FRAGMENT_TYPE, fragmentType);
        args.putSerializable(TEACHER_INFO, teacherInfo);
        args.putSerializable(CALLBACK, callback);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragmentType = getArguments().getString(FRAGMENT_TYPE);
            teacherInfo = (TeacherInfo) getArguments().getSerializable(TEACHER_INFO);
            callback = (Controller.Callback)getArguments().getSerializable(CALLBACK);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        if("practice".equals(fragmentType)){
            view = inflater.inflate(R.layout.fragment_teacher_practice_page, container, false);
            controller = new com.edu_app.controller.teacher.practice.PageController(this, view, teacherInfo);
        } else if("practice_info".equals(fragmentType)) {
            view = inflater.inflate(R.layout.fragment_teacher_practice_info, container, false);
            controller = new PracticeInfoController(this, view, teacherInfo);
        } else if("course".equals(fragmentType)){
            view = inflater.inflate(R.layout.fragment_teacher_course, container, false);
            controller = new CoursePageController(this, view, teacherInfo);
        } else if("live_stream".equals(fragmentType)){
            view = inflater.inflate(R.layout.fragment_teacher_live_stream, container, false);
            controller = new StreamPageController(this, view, teacherInfo);
        } else if("question_info".equals(fragmentType)){
            view = inflater.inflate(R.layout.fragment_teacher_question_info, container, false);
            controller = new QuestionInfoController(view, this);
        }
        controller.bindCallback(callback);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        controller.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConfigurationChanged(Configuration config){
        super.onConfigurationChanged(config);
        controller.onConfigurationChanged(config);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        controller.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public Controller getController(){
        return controller;
    }
}
