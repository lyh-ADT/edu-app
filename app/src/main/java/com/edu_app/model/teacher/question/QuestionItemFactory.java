package com.edu_app.model.teacher.question;

import android.util.Log;

import com.edu_app.model.FillBlankQuestion;
import com.edu_app.model.Question;
import com.edu_app.model.SelectQuestion;
import com.edu_app.model.teacher.practice.QuestionItem;

public class QuestionItemFactory {
    public static QuestionItem newInstance(String type, Question model){
        QuestionItem question=null;
        if(model == null || !type.equals(model.getQuestionType())){
            model = null;
        }
        if(Question.QUESTION_TYPE_CHOICE.equals(type)){
            question = new SelectQuestionItem((SelectQuestion)model);
        } else if(Question.QUESTION_TYPE_FILL.equals(type)){
            question = new FillBlankQuestionItem((FillBlankQuestion)model);
        } else if(Question.QUESTION_TYPE_SHORT_ANSWER.equals(type)){
            question = new QuestionItem(model);
        } else {
            Log.e("QuestionItemFactory", "没有这种类型的问题："+type);
        }
        return question;
    }
}
