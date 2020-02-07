package com.edu_app.model.teacher.practice;

import androidx.core.util.Pair;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.teacher.Model;
import com.edu_app.model.Practice;

import java.util.ArrayList;
import java.util.List;

public class PracticeItem extends Practice implements Model {
    public PracticeItem(String title){
        super(title);
    }

    @Override
    public List<Pair<Integer, Object>> getShowField() {
        ArrayList<Pair<Integer, Object>> list = new ArrayList<>();
        list.add(new Pair<Integer, Object>(R.id.practice_item_title, getTitle()));
        return list;
    }

    @Override
    public void setController(Controller controller) {

    }
}
