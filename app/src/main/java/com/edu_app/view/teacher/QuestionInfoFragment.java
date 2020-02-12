package com.edu_app.view.teacher;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.controller.teacher.question.FillBlankQuestionController;
import com.edu_app.controller.teacher.question.SelectQuestionController;
import com.edu_app.controller.teacher.question.ShortAnswerQuestionController;
import com.edu_app.model.Question;
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
        String type = question.getQuestionType();
        View view;
        if(Question.QUESTION_TYPE_CHOICE.equals(type)){
            view = inflater.inflate(R.layout.fragment_teacher_question_info_select, container, false);
            controller = new SelectQuestionController(view, question, editable);
        } else if(Question.QUESTION_TYPE_FILL.equals(type)){
            view = inflater.inflate(R.layout.fragment_teacher_question_info_fill_blank, container, false);
            controller = new FillBlankQuestionController(view, question, editable);
        } else {
            view = inflater.inflate(R.layout.fragment_teacher_question_info_short_answer, container, false);
            controller = new ShortAnswerQuestionController(view, question, editable);
        }
        return view;
    }
}
