package com.edu_app.controller.teacher.practice;

import android.view.View;
import android.widget.TextView;

import com.edu_app.R;
import com.edu_app.model.Question;

public class AddPracticeItemController {
    private static View.OnClickListener clickItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: 进入问题详情
        }
    };

    private Question model;
    private View view;

    public AddPracticeItemController(View view, Question model){
        this.view = view;
        this.model = model;
        setValues();
        bindListener();
    }

    private void setValues(){
        TextView title = view.findViewById(R.id.order_number_text);
        title.setText("题目"+model.getOrderNumber()+":"+model.getQuestion());
    }

    private void bindListener(){
        view.setOnClickListener(clickItemListener);
    }


}
