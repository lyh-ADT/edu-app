package com.edu_app.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TeacherPractice {
    private final String Host = "192.168.123.22:2000";// TODO: 修改为服务器地址
    private TeacherInfo info;
    private List<TeacherPracticeItem> practiceList = new ArrayList<>();

    public TeacherPractice(TeacherInfo info){
        this.info = info;
        getPracticeList();
    }

    public void getPracticeList(){
        // TODO: 实现
        practiceList.add(new TeacherPracticeItem());
        practiceList.add(new TeacherPracticeItem());
        practiceList.add(new TeacherPracticeItem());
        practiceList.add(new TeacherPracticeItem());
    }

    public void addPractice(TeacherPracticeItem item){
        // TODO: 实现
        practiceList.add(item);
    }

    public void deletePractice(String itemId){
        // TODO: 实现
        practiceList.remove(0);
    }

    public int getPracticeItemCount(){
        return practiceList.size();
    }

    public TeacherPracticeItem getPracticeItemAt(int i){
        return practiceList.get(i);
    }
}

