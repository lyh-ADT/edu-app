package com.edu_app.model.teacher.practice;

import androidx.core.util.Pair;

import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.teacher.Model;
import com.edu_app.model.teacher.TeacherInfo;

import java.util.List;

public class Judge implements Model {
    private TeacherInfo teacherInfo;

    public Judge(TeacherInfo teacherInfo){
        this.teacherInfo = teacherInfo;
    }

    @Override
    public List<Pair<Integer, Object>> getShowField() {
        return null;
    }

    @Override
    public void setController(Controller controller) {

    }
}
