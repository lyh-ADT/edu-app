package com.edu_app.model.teacher.addquestion;

import android.util.Log;

import com.edu_app.model.Question;

public class QuestionItemFactory {
    public static Question newInstance(String type){
        Question question=null;
        if("select".equals(type)){
            question = new SelectQuestionItem();
        } else if("fill_blank".equals(type)){
            question = null;
        } else if("short_answer".equals(type)){
            question = null;
        } else {
            Log.e("QuestionItemFactory", "没有这种类型的问题："+type);
        }
        return question;
    }
}
