package com.edu_app.model.teacher.practice;

import androidx.core.util.Pair;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.controller.teacher.practice.StudentListController;
import com.edu_app.model.NetworkUtility;
import com.edu_app.model.Practice;
import com.edu_app.model.teacher.Model;
import com.edu_app.model.teacher.TeacherInfo;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentPractice implements Model {
    private List<StudentPracticeItem> practices = new ArrayList<>();
    private StudentListController controller;
    private TeacherInfo teacherInfo;


    public StudentPractice(TeacherInfo teacherInfo){
        this.controller = controller;
        this.teacherInfo = teacherInfo;
    }

    public int getPracticeCount(){
        return practices.size();
    }

    public String getAuthorNameAt(int i){
        return practices.get(i).getAuthorName();
    }

    public StudentPracticeItem getPracticeAt(int i){
        return practices.get(i);
    }

    @Override
    public List<Pair<Integer, Object>> getShowField() {
        if(controller == null){
            return null;
        }
        ArrayList<Pair<Integer, Object>> list = new ArrayList<>();
        list.add(new Pair<Integer, Object>(R.id.title, controller.getPracticeItem().getTitle()));
        return list;
    }

    @Override
    public void setController(Controller controller) {
        this.controller = (StudentListController) controller;
    }

    public void getPracticeList(){
        new Thread(){
            @Override
            public void run(){
                try {
                    practices = NetworkUtility.getToJson(teacherInfo.getHost()+"/judge_practice?practiceId="+controller.getPracticeItem().getId(), teacherInfo.getUID(), new TypeToken<List<StudentPracticeItem>>(){}.getType());
                    controller.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                    controller.error("网络异常");
                }
            }
        }.start();
    }
}
