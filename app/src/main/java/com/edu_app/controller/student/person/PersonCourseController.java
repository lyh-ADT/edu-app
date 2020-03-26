package com.edu_app.controller.student.person;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edu_app.R;
import com.edu_app.controller.student.Controller;
import com.edu_app.model.NetworkUtility;

import java.io.IOException;

public class PersonCourseController extends Controller implements View.OnClickListener {
    private final AppCompatActivity activity;
    private Button addBt;
    private Button getBt;
    private JSONArray courseNames;
    private String uid;
    private Boolean getSuccess=false;
    private Boolean setSuccess=false;
    private String inviteCode;

    public PersonCourseController(AppCompatActivity activity) {
        this.activity = activity;
        this.uid = this.activity.getIntent().getExtras().get("uid").toString();


    }

    public void setOnClick() {
        getBt = activity.findViewById(R.id.personPage_stuClass_getClassBt);
        getBt.setOnClickListener(this);
        addBt = activity.findViewById(R.id.personPage_stuClass_addClassBt);
        addBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personPage_stuClass_addClassBt:
                inviteCode = ((EditText) this.activity.findViewById(R.id.personPage_stuClass_addClassText)).getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        postClassId(inviteCode);
                    }
                }).start();
                try {
                    int count=0;
                    while (!setSuccess && count<20){
                        Thread.sleep(100);
                        count++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                showDialog();
                break;
            case R.id.personPage_stuClass_getClassBt:
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        getCourseName();
                    }
                }).start();
                try {
                    int count=0;
                    while (!getSuccess && count<20){
                        Thread.sleep(100);
                        count++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String courseName = "";

                if(getSuccess){
                    for(int i=0,len=courseNames.size();i<len;i++){
                        courseName += courseNames.get(i);
                        courseName +="\n";
                    }
                }else {
                    courseName = "获取失败";
                }

                ((TextView) this.activity.findViewById(R.id.personPage_stuClass_getClassText)).setText(courseName);
                break;


        }
    }

    private void getCourseName() {
        try {
            String body = "{\"stuUid\":\"" + this.uid + "\"}";
            String response = NetworkUtility.postRequest("http://139.159.176.78:8081/stuGetClass", body);
            Log.e("error",body);
            JSONObject jsonObject = JSONObject.parseObject(response);

            getSuccess = jsonObject.getBoolean("success");
            if(getSuccess){
                courseNames = jsonObject.getJSONArray("data");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void postClassId(String inviteCode) {
//        TODO:上传班级代码，添加到个人信息中去
        try {
            String body = "{\"stuUid\":\"" + this.uid + "\",\"inviteCode\":\"" + inviteCode + "\"}";
            Log.e("error",body);
            String response = NetworkUtility.postRequest("http://139.159.176.78:8081/stuSetClass", body);
            JSONObject jsonObject = JSONObject.parseObject(response);
            setSuccess = jsonObject.getBoolean("success");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this.activity);
        dialog.setTitle("添加班级提示");
        if(setSuccess){
            dialog.setMessage("添加班级成功");

        }else {
            dialog.setMessage("添加班级失败");

        }
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}