package com.edu_app.model;

public class TeacherPractice {
    private final String Host = "192.168.123.22:2000";// TODO: 修改为服务器地址
    private TeacherInfo info;

    public TeacherPractice(TeacherInfo info){
        this.info = info;
    }

    public void getPracticeList(){
        // TODO: 实现
    }

    public void addPractice(TeacherPracticeItem item){
        // TODO: 实现
    }

    public void deletePractice(String itemId){
        // TODO: 实现
    }
}

