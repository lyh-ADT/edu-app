package com.edu_app.view.teacher;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu_app.R;
import com.edu_app.controller.teacher.practice.AddPracticeController;
import com.edu_app.controller.teacher.practice.PageController;
import com.edu_app.model.teacher.TeacherInfo;

/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 * Use the {@link Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment extends androidx.fragment.app.Fragment {
    private static final String FRAGMENT_TYPE = "fragment_type";

    private String fragmentType;
    private TeacherInfo teacherInfo;

    public Fragment(TeacherInfo teacherInfo) {
        this.teacherInfo = teacherInfo;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param fragmentType Parameter 1.
     * @return A new instance of fragment Fragment.
     */
    public static Fragment newInstance(String fragmentType, TeacherInfo teacherInfo) {
        Fragment fragment = new Fragment(teacherInfo);
        Bundle args = new Bundle();
        args.putString(FRAGMENT_TYPE, fragmentType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragmentType = getArguments().getString(FRAGMENT_TYPE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        if("practice".equals(fragmentType)){
            view = inflater.inflate(R.layout.fragment_teacher_practice_page, container, false);
            new PageController(this, view, teacherInfo);
        } else if("add_practice".equals(fragmentType)) {
            view = inflater.inflate(R.layout.fragment_teacher_add_practice_page, container, false);
            new AddPracticeController(this, view);
        }
        return view;
    }
}
