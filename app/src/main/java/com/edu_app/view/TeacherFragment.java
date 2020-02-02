package com.edu_app.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu_app.R;
import com.edu_app.controller.TeacherPracticeController;
import com.edu_app.model.TeacherInfo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherFragment extends Fragment {
    private static final String FRAGMENT_TYPE = "fragment_type";

    private String fragmentType;
    private TeacherInfo teacherInfo;

    public TeacherFragment(TeacherInfo teacherInfo) {
        this.teacherInfo = teacherInfo;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param fragmentType Parameter 1.
     * @return A new instance of fragment TeacherFragment.
     */
    public static TeacherFragment newInstance(String fragmentType, TeacherInfo teacherInfo) {
        TeacherFragment fragment = new TeacherFragment(teacherInfo);
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
            view = inflater.inflate(R.layout.fragment_practice_page, container, false);
            new TeacherPracticeController(this, view, teacherInfo);
        }
        return view;
    }
}
