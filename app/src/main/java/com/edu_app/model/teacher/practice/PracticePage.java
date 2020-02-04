package com.edu_app.model.teacher.practice;

import androidx.core.util.Pair;

import com.edu_app.controller.teacher.Controller;
import com.edu_app.controller.teacher.practice.PageController;
import com.edu_app.model.teacher.Model;
import com.edu_app.model.NetworkUtility;
import com.edu_app.model.teacher.TeacherInfo;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PracticePage implements Model {
    private final String Host = "http://192.168.123.22:2000";// TODO: 修改为服务器地址
    private TeacherInfo info;
    private List<PracticeItem> practiceList = new ArrayList<>();
    private PageController pageController;

    public PracticePage(TeacherInfo info ){
        this.info = info;
        getPracticeList();
    }

    public void getPracticeList(){
        new Thread(){
            @Override
            public void run(){
                try {
                    practiceList = NetworkUtility.getToJson(Host+"/practice", info.getUID(), new TypeToken<List<PracticeItem>>(){}.getType());
                    pageController.handler.sendEmptyMessage(0);
                } catch (IOException e) {
                    e.printStackTrace();
                    pageController.error("网络异常，获取不到练习列表");
                }
            }
        }.start();
    }

    public void addPractice(PracticeItem item){
        // TODO: 实现网络请求
        practiceList.add(item);
    }

    public void deletePractice(final PracticeItem item){
        new Thread(){
            @Override
            public void run(){
                try {
                    String response = NetworkUtility.postRequest(Host+"/delete_practice", info.getUID(), item.getId().getBytes());
                    if("success".equals(response)){
                        practiceList.remove(item);
                        pageController.handler.sendEmptyMessage(0);
                    }else{
                        pageController.error(response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public int getPracticeItemCount(){
        return practiceList.size();
    }

    public PracticeItem getPracticeItemAt(int i){
        return practiceList.get(i);
    }

    @Override
    public List<Pair<Integer, Object>> getShowField() {
        return null;
    }

    @Override
    public void setController(Controller controller){
        this.pageController = (PageController)controller;
    }
}

