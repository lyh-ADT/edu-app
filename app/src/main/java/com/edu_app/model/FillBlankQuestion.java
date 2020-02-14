package com.edu_app.model;

import java.util.ArrayList;
import java.util.List;

public class FillBlankQuestion extends Question {
    private List<String> answers = new ArrayList<>();

    public FillBlankQuestion(){
        super(0, Question.QUESTION_TYPE_FILL, null);
    }

    public void addBlank(String answer){
        answers.add(answer);
    }

    public void removeBlank(int i){
        answers.remove(i);
    }

    public int blankCount(){
        return answers.size();
    }

    public String getBlank(int i){
        return answers.get(i);
    }

    @Override
    public String getAnswer(){
        StringBuilder sb = new StringBuilder();
        for(String i : answers){
            sb.append(i);
            sb.append(',');
        }
        if(sb.length() > 0){
            sb.deleteCharAt(sb.length()-1);
            return sb.toString();
        } else {
            return null;
        }
    }
}
