package com.edu_app.view.teacher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.controller.teacher.question.ShortAnswerQuestionController;
import com.edu_app.model.teacher.practice.QuestionItem;

public class QuestionInfoFragment extends Fragment {
    private static final String QUESTION = "question";
    private static final String EDITABLE = "editable";

    private QuestionItem question;
    private Controller controller;
    private boolean editable;


    public QuestionInfoFragment() {}

    public static QuestionInfoFragment newInstance(QuestionItem question, boolean editable) {
        QuestionInfoFragment fragment = new QuestionInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(QUESTION, question);
        args.putBoolean(EDITABLE, editable);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = (QuestionItem) getArguments().getSerializable(QUESTION);
            editable = getArguments().getBoolean(EDITABLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_teacher_question_info_short_answer, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new ShortAnswerQuestionController(view, question, editable);

    }
}
