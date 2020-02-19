package com.edu_app.model.teacher.practice;

import androidx.core.util.Pair;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.Class;
import com.edu_app.model.NetworkUtility;
import com.edu_app.model.Question;
import com.edu_app.model.teacher.Model;
import com.edu_app.model.Practice;
import com.edu_app.model.teacher.TeacherInfo;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PracticeItem implements Model {
    private Practice practice;
    private final static int FULL_SCORE = 999;
    private int fullScore = 0;
    private List<Class> classList = new ArrayList<>();

    public PracticeItem(Practice practice){
        if(practice == null){
            practice = new Practice();
        }
        this.practice = practice;
    }

    @Override
    public List<Pair<Integer, Object>> getShowField() {
        ArrayList<Pair<Integer, Object>> list = new ArrayList<>();
        list.add(new Pair<Integer, Object>(R.id.practice_item_title, practice.getTitle()));
        list.add(new Pair<Integer, Object>(R.id.class_text, practice.getClassId()));
        return list;
    }

    @Override
    public void setController(Controller controller) {

    }

    public interface UploadCallback {
        void success();
        void fail(String reason);
    }

    public void uploadPractice(final TeacherInfo teacherInfo, final UploadCallback callback){
        new Thread(){
            @Override
            public void run(){
                calcFullScore();
                String response = null;
                try {
                    response = NetworkUtility.postRequest(teacherInfo.getHost()+"/add_practice", teacherInfo.getUID(), practice);
                    if("success".equals(response)){
                        callback.success();
                    } else {
                        callback.fail(response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.fail("网络异常, 添加失败");
                }
            }
        }.start();
    }

    public void getClassesList(final TeacherInfo info, final UploadCallback callback){
        new Thread(){
            @Override
            public void run(){
                try {
                    classList = NetworkUtility.getToJson(info.getHost()+"/classList", info.getUID(), new TypeToken<List<Class>>(){}.getType());
                    callback.success();
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.fail("网络异常，获取不到班级列表");
                }
            }
        }.start();
    }

    public int getQuestionCount(){
        return practice.getQuestions().size();
    }

    public boolean addQuestion(QuestionItem questionItem){
        if(fullScore + questionItem.getScore() > FULL_SCORE){
            return false;
        }
        practice.getQuestions().add(questionItem.getEntity());
        return true;
    }

    public QuestionItem getQuestionAt(int pos){
        Question question = practice.getQuestions().get(pos);
        return new QuestionItem(question);
    }

    public void deleteQuestion(QuestionItem questionItem){
        fullScore -= questionItem.getScore();
        practice.getQuestions().remove(questionItem.getEntity());
    }

    public String getId(){
        return practice.getId();
    }

    public String getTitle(){
        return practice.getTitle();
    }

    public Practice getEntity(){
        return practice;
    }

    public void setTitle(String title){
        practice.setTitle(title);
    }

    public List<Class> getClassList() {
        return classList;
    }

    public String getClassId(){
        return practice.getClassId();
    }

    public void setClassId(String id){
        practice.setClassId(id);
    }

    private void calcFullScore(){
        int fullScore = 0;
        for(Question q : practice.getQuestions()){
            fullScore += q.getScore();
        }
        practice.setFullScore(fullScore);
    }
}
