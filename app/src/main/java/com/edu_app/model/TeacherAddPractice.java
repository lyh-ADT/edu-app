package com.edu_app.model;

import java.util.ArrayList;
import java.util.List;

public class TeacherAddPractice {
    private List<Question> questions = new ArrayList<>();

    public void addQuestion(Question q){
        questions.add(q);
    }

    public int getQuestionCount(){
        return questions.size();
    }

    public Question getQuestionAt(int i){
        return questions.get(i);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
