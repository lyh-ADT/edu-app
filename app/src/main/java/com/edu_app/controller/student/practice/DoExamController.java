package com.edu_app.controller.student.practice;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu_app.R;
import com.edu_app.model.Practice;

import java.util.Map;

public class DoExamController implements View.OnClickListener {
    private final AppCompatActivity activity;
    private Practice practice;
    private RecyclerView recycler;
    private Button bu_sumbmit;
    private DoExamAdapter adapter;
    private Map<Integer, String> answer;

    public DoExamController(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setView() {
        practice = (Practice) activity.getIntent().getSerializableExtra("data");
        recycler = activity.findViewById(R.id.practicePage_practice_doexam_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL, false));
        adapter = new DoExamAdapter(activity, practice.getQuestions());
        recycler.setAdapter(adapter);

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
//
//        Set<Map.Entry<Integer, String>> set = answer.entrySet();
//        Iterator<Map.Entry<Integer, String>> iterator = set.iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<Integer, String> entry = iterator.next();
//            Log.e("error","键是：" + entry.getKey() + "值是：" + entry.getValue());
//        }
//        Log.e("error","============================");
    }

    public void setKeyDown() {
        AlertDialog.Builder dialog = new AlertDialog.Builder((Context) activity);
        dialog.setTitle("提交答案提示");
        dialog.setMessage("确认退出练习并提交答案吗？");
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
}
