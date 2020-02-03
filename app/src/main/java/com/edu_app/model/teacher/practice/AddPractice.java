package com.edu_app.model.teacher.practice;

import com.edu_app.model.Question;
import com.edu_app.model.teacher.TeacherInfo;

import java.util.ArrayList;
import java.util.List;

public class AddPractice {
    private List<Question> questions = new ArrayList<>();
    private TeacherInfo teacherInfo;

    public AddPractice(TeacherInfo teacherInfo){
        this.teacherInfo = teacherInfo;
    }

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

    public TeacherInfo getTeacherInfo() {
        return teacherInfo;
    }
}
