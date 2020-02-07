package com.edu_app.view.teacher;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.controller.teacher.addquestion.FillBlankQuestionController;
import com.edu_app.controller.teacher.addquestion.SelectQuestionController;
import com.edu_app.controller.teacher.addquestion.ShortAnswerQuestionController;
import com.edu_app.model.teacher.practice.QuestionItem;

public class AddQuestionInfoFragment extends Fragment {
    private static final String QUESTION = "question";

    private QuestionItem question;
    private Controller controller;


    public AddQuestionInfoFragment() {}

    public static AddQuestionInfoFragment newInstance(QuestionItem question) {
        AddQuestionInfoFragment fragment = new AddQuestionInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(QUESTION, question);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = (QuestionItem) getArguments().getSerializable(QUESTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String type = question.getQuestionType();
        View view = null;
        if("select".equals(type)){
            view = inflater.inflate(R.layout.fragment_teacher_question_info_select, container, false);
            controller = new SelectQuestionController(view, question);
        } else if("fill_blank".equals(type)){
            view = inflater.inflate(R.layout.fragment_teacher_question_info_fill_blank, container, false);
            controller = new FillBlankQuestionController(view, question);
        } else {
            view = inflater.inflate(R.layout.fragment_teacher_question_info_short_answer, container, false);
            controller = new ShortAnswerQuestionController(view, question);
        }
        return view;
    }
}
