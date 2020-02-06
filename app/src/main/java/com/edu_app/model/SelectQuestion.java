package com.edu_app.model;

import java.util.Map;

public class SelectQuestion extends Question {
    private Map<String, String> selections;

    public SelectQuestion(){
        super(0, "select", "");
    }

    public boolean addSelection(String order, String answer){
        if(selections.containsKey(order)){
            return false;
        }
        selections.put(order, answer);
        return true;
    }

    @Override
    public void setAnswer(String answer) throws IllegalArgumentException{
        if(selections.containsKey(answer)){
            super.setAnswer(answer);
        }else{
            throw new IllegalArgumentException("答案中没有这个选项:"+answer);
        }
    }
}
