package com.edu_app.view.student.pracitce;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu_app.R;
import com.edu_app.controller.student.practice.BrowseExercisesController;
import com.edu_app.controller.student.practice.ExamAdapter;
import com.edu_app.controller.student.practice.GetPracticeList;
import com.edu_app.controller.student.practice.OnItemClickListener;
import com.edu_app.model.Practice;
import com.edu_app.view.student.activityStuFunction;

import java.util.ArrayList;

public class activitySubjectEnglish extends AppCompatActivity {
    private RecyclerView englishrecycler;
    private ArrayList<Practice> practicelist;
    private String uid;
    private String subject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
//        初始化视图
        setContentView(R.layout.activity_stu_practice_subjectenglish);
        englishrecycler = findViewById(R.id.practicePage_practice_english_recycler);

//        初始化数据
        uid = getIntent().getExtras().get("uid").toString();
        subject = getIntent().getExtras().get("subject").toString();
        practicelist = new GetPracticeList(uid,subject).getPracticeList();
//        设置适配器
        ExamAdapter adapter = new ExamAdapter(activitySubjectEnglish.this, practicelist);
        englishrecycler.setAdapter(adapter);
//        设置layoutManager
        LinearLayoutManager layoutmanager = new LinearLayoutManager(activitySubjectEnglish.this, LinearLayoutManager.VERTICAL, false);
        englishrecycler.setLayoutManager(layoutmanager);
//        设置监听器
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                BrowseExercisesController controller = new BrowseExercisesController(activitySubjectEnglish.this);
                controller.setData(practicelist.get(position),uid);
                controller.onClick(v);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtras(this.getIntent().getExtras());
            intent.setClass(activitySubjectEnglish.this, activityStuFunction.class);
            startActivity(intent);
            activitySubjectEnglish.this.finish();

        }
        return true;
    }
}
