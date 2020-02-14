package com.edu_app.model.teacher.practice;

import androidx.core.util.Pair;

import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.Practice;
import com.edu_app.model.teacher.Model;

import java.util.List;

public class StudentPracticeItem implements Model {
    private String authorId;
    private String authorName;
    private Practice practice;

    public String getAuthorId() {
        return authorId;
    }

    public Practice getPractice() {
        return practice;
    }

    public String getAuthorName() {
        return authorName;
    }

    @Override
    public List<Pair<Integer, Object>> getShowField() {
        return null;
    }

    @Override
    public void setController(Controller controller) {

    }
}
