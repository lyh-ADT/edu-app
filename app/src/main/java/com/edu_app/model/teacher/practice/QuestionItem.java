package com.edu_app.model.teacher.practice;

import androidx.core.util.Pair;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.teacher.Model;
import com.edu_app.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionItem extends Question implements Model {

    public QuestionItem(){}

    public QuestionItem(Question question){
        super(question.getOrderNumber(), question.getQuestionType(), question.getQuestion());
    }

    @Override
    public List<Pair<Integer, Object>> getShowField() {
        ArrayList<Pair<Integer, Object>> list = new ArrayList<>();
        list.add(new Pair<Integer, Object>(R.id.order_number_text, "题目"+getOrderNumber()+":"+getQuestion()));
        return list;
    }

    @Override
    public void setController(Controller controller) {

    }
}
