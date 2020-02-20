package com.edu_app.controller.student.practice;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.edu_app.R;
import com.edu_app.model.NetworkUtility;
import com.edu_app.model.Practice;

import java.io.IOException;

/**
 * 控制查看练习题的页面
 */
public class LookExamController {
    private final AppCompatActivity activity;
    private final String  uid;
    private final String practiceId;
    private Practice practice;
    private RecyclerView recycler;
    private Button bu_sumbmit;
    private LookExamAdapter adapter;

    private Object getSuccess;
    private JSONObject data;

    public LookExamController(AppCompatActivity activity) {
        this.activity = activity;
        this.uid = this.activity.getIntent().getExtras().get("uid").toString();
        this.practiceId = this.activity.getIntent().getExtras().get("practiceId").toString();

    }

    public void setView() {
        activity.setContentView(R.layout.activity_stu_practice_lookexam_recycler);
        getPractice();

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//        everyQuestionDetail = (HashMap)getAllData().get("everyQuestionDetail");
        recycler = activity.findViewById(R.id.practicePage_practice_lookexam_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        adapter = new LookExamAdapter(activity, data);
        recycler.setAdapter(adapter);
//        添加分割线
        recycler.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
    }


    public void setKeyDown() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("退出提示");
        dialog.setMessage("确认退出查看练习吗？");
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
            String response = NetworkUtility.postRequest("http://139.159.176.78:8081/stuGetPracticeToLook", body);
            JSONObject jsonObject = JSONObject.parseObject(response);
            getSuccess = jsonObject.getBoolean("success");

            data = jsonObject.getJSONObject("data");
            Log.e("error",data.toJSONString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

