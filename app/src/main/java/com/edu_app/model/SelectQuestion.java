package com.edu_app.model;

import java.util.ArrayList;
import java.util.List;

public class SelectQuestion extends Question {
    private List<String> selections = new ArrayList<>();

    public SelectQuestion(){
        super(0, Question.QUESTION_TYPE_CHOICE, null);
    }

    public void addSelection(String answer){
        selections.add(answer);
    }

    public int getSelectionCount(){
        return selections.size();
    }

    public String getSelectionAt(int i){
        return selections.get(i);
    }

    public void remove(int index){
        selections.remove(index);
    }
}
