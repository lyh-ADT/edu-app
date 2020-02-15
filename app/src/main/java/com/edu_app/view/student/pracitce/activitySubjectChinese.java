package com.edu_app.view.student.pracitce;

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
import com.edu_app.controller.student.practice.OnItemClickListener;
import com.edu_app.model.Practice;

import java.util.ArrayList;

public class activitySubjectChinese extends AppCompatActivity {
    private RecyclerView chineserecycler;
    private ArrayList<Practice> practicelist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
//        初始化视图
        setContentView(R.layout.activity_stu_practice_subjectchinese);
        chineserecycler = (RecyclerView) findViewById(R.id.practicePage_practice_chinese_recycler);
//        初始化数据
        practicelist = (ArrayList<Practice>) getIntent().getSerializableExtra("alldata");

//        设置适配器
        ExamAdapter adapter = new ExamAdapter(activitySubjectChinese.this, practicelist);
        chineserecycler.setAdapter(adapter);
//        设置layoutManager
        LinearLayoutManager layoutmanager = new LinearLayoutManager(activitySubjectChinese.this, LinearLayoutManager.VERTICAL, false);
        chineserecycler.setLayoutManager(layoutmanager);
//        设置监听器
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                BrowseExercisesController controller = new BrowseExercisesController(activitySubjectChinese.this);
                controller.setData(practicelist.get(position));
                controller.onClick(v);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            activitySubjectChinese.this.finish();

        }
        return true;
    }
}
