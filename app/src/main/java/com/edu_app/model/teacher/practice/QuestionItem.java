package com.edu_app.model.teacher.practice;

import androidx.core.util.Pair;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.teacher.Model;
import com.edu_app.model.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionItem implements Model, Serializable {
    protected Question question;

    public QuestionItem(Question question){
        this.question = question;
    }



    @Override
    public List<Pair<Integer, Object>> getShowField() {
        ArrayList<Pair<Integer, Object>> list = new ArrayList<>();
        list.add(new Pair<Integer, Object>(R.id.order_number_text, "题目"+question.getOrderNumber()+":"+question.getQuestion()));
        return list;
    }

    @Override
    public void setController(Controller controller) {

    }

    public String getQuestion() {
        return question.getQuestion();
    }

    public String getAnswer() {
        return question.getAnswer();
    }

    public void setQuestion(String o) {
        question.setQuestion(o);
    }

    public void setAnswer(String s) {
        question.setAnswer(s);
    }

    public void setOrderNumber(int i) {
        question.setOrderNumber(i);
    }

    public String getQuestionType() {
        return question.getQuestionType();
    }
}
