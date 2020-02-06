package com.edu_app.view.student.pracitce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu_app.R;
import com.edu_app.controller.student.practice.BrowseExercisesController;
import com.edu_app.controller.student.practice.ExamAdapter;
import com.edu_app.controller.student.practice.OnItemClickListener;
import com.edu_app.model.Practice;
import com.edu_app.view.activityFunction;
import com.edu_app.view.student.person.activityPersonInfo;

import java.util.ArrayList;

public class activitySubjectEnglish extends Activity {
    private RecyclerView englishrecycler;
    private ArrayList<Practice> practicelist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
//        初始化视图
        setContentView(R.layout.activity_stu_practice_subjectenglish);
        englishrecycler = (RecyclerView) findViewById(R.id.practicePage_practice_english_recycler);

//        初始化数据
        practicelist = (ArrayList<Practice>) getIntent().getSerializableExtra("alldata");

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
                controller.setData(practicelist.get(position));
                controller.onClick(v);
            }
        });
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Intent intent = new Intent();
//            intent.setClass(activitySubjectEnglish.this, activityFunction.class);
//            startActivity(intent);
//            activitySubjectEnglish.this.finish();
//
//        }
//        return true;
//    }
}
