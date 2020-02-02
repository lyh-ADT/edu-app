package com.edu_app.controller;

import android.view.View;
import android.widget.TextView;

import com.edu_app.R;
import com.edu_app.model.TeacherPracticeItem;

public class TeacherPracticeItemController {

    private static View.OnClickListener clickItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: 进入练习详情
        }
    };
    private final TeacherPracticeItem model;
    private View view;

    public TeacherPracticeItemController(View view, TeacherPracticeItem model){
        this.view = view;
        this.model = model;
        setValues();
        bindListener();
    }

    private void setValues(){
        TextView title = view.findViewById(R.id.practice_item_title);
        title.setText(model.getTitle());
    }

    private void bindListener(){
        view.setOnClickListener(clickItemListener);
    }
}
