package com.edu_app.model.teacher.practice;

import androidx.core.util.Pair;

import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.teacher.Model;
import com.edu_app.model.teacher.TeacherInfo;

import java.util.ArrayList;
import java.util.List;

public class AddPractice implements Model {
    private List<QuestionItem> questions = new ArrayList<>();
    private TeacherInfo teacherInfo;

    public AddPractice(TeacherInfo teacherInfo){
        this.teacherInfo = teacherInfo;
    }

    public void addQuestion(QuestionItem q){
        questions.add(q);
    }

    public int getQuestionCount(){
        return questions.size();
    }

    public QuestionItem getQuestionAt(int i){
        return questions.get(i);
    }

    public List<QuestionItem> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionItem> questions) {
        this.questions = questions;
    }

    public TeacherInfo getTeacherInfo() {
        return teacherInfo;
    }

    @Override
    public List<Pair<Integer, Object>> getShowField() {
        return null;
    }

    @Override
    public void setController(Controller controller) {}
}
