package com.edu_app.controller.teacher.question;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
            answer_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String q = s.toString();
                    Log.e("error",q);
                    if(q.length() <= 0){
                        model.setAnswer(null);
                   }else{
                       model.setAnswer(q);
                    }
                }
            });
        }else{
            answer_et.setText(model.getAnswer());
            answer_et.setEnabled(false);
        }

    }
}
