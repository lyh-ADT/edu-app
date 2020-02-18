package com.edu_app.model.teacher.practice;

import androidx.core.util.Pair;

import com.edu_app.controller.teacher.Controller;
import com.edu_app.controller.teacher.practice.PageController;
import com.edu_app.model.FillBlankQuestion;
import com.edu_app.model.Practice;
import com.edu_app.model.Question;
import com.edu_app.model.SelectQuestion;
import com.edu_app.model.teacher.Model;
import com.edu_app.model.NetworkUtility;
import com.edu_app.model.teacher.TeacherInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PracticePage implements Model {
    private TeacherInfo info;
    private List<Practice> practiceList = new ArrayList<>();
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
                    String response = NetworkUtility.getRequest(info.getHost()+"/practice", info.getUID());
                    JsonParser parser = new JsonParser();
                    JsonArray array = parser.parse(response).getAsJsonArray();

                    practiceList = new ArrayList<>();
                    for(JsonElement i : array){
                        practiceList.add(parsePractice(i));
                    }
                    pageController.handler.sendEmptyMessage(0);
                } catch (IOException e) {
                    e.printStackTrace();
                    pageController.error("网络异常，获取不到练习列表");
                }
            }
        }.start();
    }

    private Practice parsePractice(JsonElement element){
        Gson gson = new Gson();
        Practice practice = gson.fromJson(element, Practice.class);
        JsonArray jsonQuestions = element.getAsJsonObject().get("questions").getAsJsonArray();
        ArrayList<Question> questions = new ArrayList<>();
        for(JsonElement i : jsonQuestions){
            String type = i.getAsJsonObject().get("questionType").getAsString();
            Question q;
            if(Question.QUESTION_TYPE_CHOICE.equals(type)){
                q = gson.fromJson(i, SelectQuestion.class);
            } else if(Question.QUESTION_TYPE_FILL.equals(type)){
                q = gson.fromJson(i, FillBlankQuestion.class);
            } else {
                q = gson.fromJson(i, Question.class);
            }
            questions.add(q);
        }
        practice.setQuestions(questions);
        return practice;
    }


    public void deletePractice(final PracticeItem item){
        new Thread(){
            @Override
            public void run(){
                try {
                    String response = NetworkUtility.postRequest(info.getHost()+"/delete_practice", info.getUID(), item.getId().getBytes());
                    if("success".equals(response)){
                        getPracticeList();
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
        return new PracticeItem(practiceList.get(i));
    }

    @Override
    public List<Pair<Integer, Object>> getShowField() {
        return null;
    }

    @Override
    public void setController(Controller controller){
        this.pageController = (PageController)controller;
    }

    public TeacherInfo getTeacherInfo(){
        return info;
    }
}

