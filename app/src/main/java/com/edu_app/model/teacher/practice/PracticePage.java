package com.edu_app.model.teacher.practice;

import com.edu_app.controller.teacher.practice.PageController;
import com.edu_app.model.Question;
import com.edu_app.model.teacher.TeacherInfo;

import java.util.ArrayList;
import java.util.List;

public class PracticePage {
    private final String Host = "192.168.123.22:2000";// TODO: 修改为服务器地址
    private TeacherInfo info;
    private List<PracticeItem> practiceList = new ArrayList<>();
    private PageController pageController;

    public PracticePage(TeacherInfo info, PageController pageController){
        this.info = info;
        this.pageController = pageController;
        getPracticeList();
    }

    public void getPracticeList(){
        // TODO: 实现网络请求
        for(int i=0; i < 10; ++i){
            PracticeItem item = new PracticeItem("练习"+String.valueOf(i));
            item.setId(String.valueOf(i));
            item.setQuestions(new ArrayList<Question>());
            practiceList.add(item);
        }
    }

    public void addPractice(PracticeItem item){
        // TODO: 实现网络请求
        practiceList.add(item);
    }

    public void deletePractice(PracticeItem item){
        // TODO: 实现网络请求
        practiceList.remove(item);
        pageController.notifyDataChanged();
    }

    public int getPracticeItemCount(){
        return practiceList.size();
    }

    public PracticeItem getPracticeItemAt(int i){
        return practiceList.get(i);
    }
}

