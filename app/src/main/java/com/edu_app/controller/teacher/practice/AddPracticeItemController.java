package com.edu_app.controller.teacher.practice;

import android.view.View;

import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.teacher.practice.QuestionItem;

public class AddPracticeItemController extends Controller {
    private static View.OnClickListener clickItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: 进入问题详情
        }
    };

    private View view;

    public AddPracticeItemController(View view, QuestionItem model){
        super(view, model);
        this.view = view;
        bindListener();
    }

    @Override
    protected void bindListener(){
        view.setOnClickListener(clickItemListener);
    }


}
