package com.edu_app.model.teacher.addquestion;

import androidx.core.util.Pair;

import com.edu_app.model.FillBlankQuestion;
import com.edu_app.model.teacher.practice.QuestionItem;

import java.util.List;

public class FillBlankQuestionItem extends QuestionItem {
    private FillBlankQuestion question;

    public FillBlankQuestionItem(FillBlankQuestion question) {
        super(question);
        if(question == null){
            question = new FillBlankQuestion();
        }
        super.question = question;
        this.question = question;
    }

    public void addBlank(String answer){
        question.addBlank(answer);
    }

    public void removeBlank(int i){
        question.removeBlank(i);
    }

    public int blankCount(){
        return question.blankCount();
    }

    public String getBlank(int i){
        return question.getBlank(i);
    }

    @Override
    public List<Pair<Integer, Object>> getShowField() {
        return null;
    }
}