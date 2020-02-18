package com.edu_app.controller.student.practice;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edu_app.model.NetworkUtility;
import com.edu_app.model.Practice;

import java.io.IOException;
import java.util.ArrayList;

public class GetPracticeList{
    private String uid;
    private String subject;
    private Boolean getSuccess;
    private JSONArray data;
    private ArrayList<Practice> practiceList;

    public GetPracticeList(String uid,String subject){
        this.subject = subject;
        this.uid = uid;
    }
    public ArrayList<Practice> getPracticeList(){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    getOriginList();
                }
            }).start();
            while (getSuccess==null){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        practiceList = new ArrayList<Practice>();
            for(int i=0,len=data.size();i<len;i++){
                JSONObject jsonObj = data.getJSONObject(0);
                Practice practice = new Practice();
                practice.setTitle(jsonObj.getString("practiceId"));
                practice.setId(jsonObj.getString("practiceId"));
                practice.setDone(jsonObj.getBoolean("isDone"));
                practiceList.add(practice);

            }
            return practiceList;
    }
    private void getOriginList(){
        try {
            String body = "{\"stuUid\":\"" + this.uid + "\",\"subject\":\"" + this.subject + "\"}";
            Log.e("error",body);
            String response = NetworkUtility.postRequest("http://139.159.176.78:8081/stuGetPracticeList", body);
            JSONObject jsonObject = JSONObject.parseObject(response);
            getSuccess = jsonObject.getBoolean("success");
            data = jsonObject.getJSONArray("data");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
