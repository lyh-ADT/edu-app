package com.edu_app.model.teacher;

import androidx.core.util.Pair;

import com.edu_app.controller.teacher.Controller;

import java.util.List;

public interface Model{
    List<Pair<Integer, Object>> getShowField();

    void setController(Controller controller);
}
