package com.edu_app.model.teacher.question;

import com.edu_app.model.FillBlankQuestion;
import com.edu_app.model.teacher.practice.QuestionItem;

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
        question.setAnswer("");
    }

    public void removeBlank(int i){
        question.removeBlank(i);
        if(question.blankCount() <= 0){
            question.setAnswer(null);
        }
    }

    public int blankCount(){
        return question.blankCount();
    }

    public String getBlank(int i){
        return question.getBlank(i);
    }
}
