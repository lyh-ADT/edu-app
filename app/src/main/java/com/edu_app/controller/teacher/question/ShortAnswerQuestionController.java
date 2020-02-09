package com.edu_app.controller.teacher.question;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.teacher.practice.QuestionItem;

public class ShortAnswerQuestionController extends Controller {
    private QuestionItem model;
    private boolean editable;

    public ShortAnswerQuestionController(View view, QuestionItem model, boolean editable) {
        super(view, model);
        this.model = model;
        this.editable = editable;
        bindListener();
    }

    @Override
    protected void bindListener(){
        final EditText answer_et = view.findViewById(R.id.correct_answer);
        if(editable){
            answer_et.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    String q = ((EditText)v).getText().toString();
                    if(q.length() <= 0){
                        model.setAnswer(null);
                    }else{
                         model.setAnswer(q);
                    }
                    return false;
                }
            });
        }else{
            answer_et.setText(model.getAnswer());
            answer_et.setEnabled(false);
        }

    }
}
