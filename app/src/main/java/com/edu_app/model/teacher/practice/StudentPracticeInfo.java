package com.edu_app.model.teacher.practice;

import androidx.core.util.Pair;

import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.Practice;
import com.edu_app.model.teacher.Model;

import java.util.List;

public class StudentPracticeInfo implements Model {
    public String getPracticeId() {
        return practiceId;
    }

    private String practiceId;
    private String stuId;
    private String stuName;
    private boolean isDone;

    public String getStuId() {
        return stuId;
    }


    public String getStuName() {
        return stuName;
    }

    @Override
    public List<Pair<Integer, Object>> getShowField() {
        return null;
    }

    @Override
    public void setController(Controller controller) {

    }
}
