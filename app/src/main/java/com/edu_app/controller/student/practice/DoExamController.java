package com.edu_app.controller.student.practice;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edu_app.R;
import com.edu_app.model.NetworkUtility;
import com.edu_app.model.Practice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DoExamController implements View.OnClickListener {
    private final AppCompatActivity activity;
    private final String practiceId;
    private final String uid;
    private Practice practice;
    private RecyclerView recycler;
    private Button bu_sumbmit;
    private DoExamAdapter adapter;
    private Map<String, String> answer;
    private Boolean getSuccess;
    private JSONArray data;
    private Boolean setSuccess;

    public DoExamController(AppCompatActivity activity) {
        this.activity = activity;
        this.uid = this.activity.getIntent().getExtras().get("uid").toString();
        this.practiceId = this.activity.getIntent().getExtras().get("practiceId").toString();
        Log.e("error",this.uid+practiceId);
    }

    public void setView() {
        getPractice();
        while(getSuccess==null){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(getSuccess){
            recycler = activity.findViewById(R.id.practicePage_practice_doexam_recycler);
            recycler.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL, false));
            adapter = new DoExamAdapter(activity, data);
            recycler.setAdapter(adapter);
        }else {

            Toast.makeText(this.activity,data.getString(0),Toast.LENGTH_SHORT);
        }

    }

    public void setSubmitListener() {
        bu_sumbmit = activity.findViewById(R.id.practicePage_practice_doexam_btSubmit);
        bu_sumbmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
// TODO:获取返回的答案
        answer = adapter.getAnswer();
        setKeyDown();
    }

    public void setKeyDown() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("提交答案提示");
        dialog.setMessage("确认退出练习并提交答案吗？");
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        postAnswer();
                    }
                }).start();
                activity.finish();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void postAnswer() {
        Map data = new HashMap();
        data.put("stuUid",this.uid);
        data.put("practiceId",this.practiceId);
        data.put("stuAnswer",answer);
        String body = JSON.toJSONString(data);
        Log.e("error",body);
        String response = null;
        try {
            response = NetworkUtility.postRequest("http://139.159.176.78:8081/stuPostAnswer", body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(response);
        setSuccess = jsonObject.getBoolean("success");

    }

    public void getPractice() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }).start();

    }

    private void getData() {
        try {
            String body = "{\"stuUid\":\"" + this.uid + "\",\"practiceId\":\"" + this.practiceId + "\"}";
            Log.e("error", body);
            String response = NetworkUtility.postRequest("http://139.159.176.78:8081/stuGetPracticeToDo", body);
            JSONObject jsonObject = JSONObject.parseObject(response);
            getSuccess = jsonObject.getBoolean("success");
            data = jsonObject.getJSONArray("data");
            Log.e("error",data.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
