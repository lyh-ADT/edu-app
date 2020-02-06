package com.edu_app.view.student.pracitce;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.widget.Button;

import com.edu_app.R;
import com.edu_app.controller.student.practice.PracticeMainController;

public class fragmentPractice extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_practice, container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PracticeMainController mainController = new PracticeMainController(getActivity());
        Button btenglish = getActivity().findViewById(R.id.practicePage_subject_english);
        Button btmath = getActivity().findViewById(R.id.practicePage_subject_math);
        Button btchinese = getActivity().findViewById(R.id.practicePage_subject_chinese);
        btmath.setOnClickListener(mainController);
        btenglish.setOnClickListener(mainController);
        btchinese.setOnClickListener(mainController);


    }
}
