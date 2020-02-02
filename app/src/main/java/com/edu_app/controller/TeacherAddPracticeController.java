package com.edu_app.controller;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.edu_app.R;
import com.edu_app.model.Question;
import com.edu_app.model.TeacherAddPractice;

public class TeacherAddPracticeController {
    private Fragment fragment;
    private TeacherAddPractice model;

    public TeacherAddPracticeController(Fragment fragment, View view){
        this.fragment = fragment;
        model = new TeacherAddPractice();
        bindListener(view);
    }

    private void bindListener(View view){
        ListView practice_list = view.findViewById(R.id.practice_list);
        final TeacherAddPracticeListAdapter adapter = new TeacherAddPracticeListAdapter(fragment.getLayoutInflater(), model);
        practice_list.setAdapter(adapter);

        Button addQuestion_btn = view.findViewById(R.id.add_question_btn);
        addQuestion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question question = new Question();
                question.setOrderNumber(model.getQuestionCount()+1);
                model.addQuestion(question);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
