package com.edu_app.controller;

import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.edu_app.R;
import com.edu_app.model.TeacherInfo;
import com.edu_app.model.TeacherPractice;
import com.edu_app.model.TeacherPracticeItem;

import java.util.List;

public class TeacherPracticeController {
    private Fragment fragment;
    private TeacherPractice model;

    public TeacherPracticeController(Fragment fragment, View view, TeacherInfo teacherInfo){
        this.fragment = fragment;
        model = new TeacherPractice(teacherInfo);
        bindListener(view);
    }

    public void setPracticeList(List<TeacherPracticeItem> list){
        for(TeacherPracticeItem i : list){
            addPractice(i);
        }
    }

    public void error(String message){
        Looper.prepare();
        Toast.makeText(fragment.getContext(), message, Toast.LENGTH_LONG).show();
        Looper.loop();
    }

    private void bindListener(View view){
        ListView practice_list = (ListView)view.findViewById(R.id.practice_list);
        final TeacherPracticeListAdapter adapter = new TeacherPracticeListAdapter(fragment.getLayoutInflater(), model);
        practice_list.setAdapter(adapter);

        Button addPractice_btn = (Button)view.findViewById(R.id.add_practice_btn);
        addPractice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 添加练习的页面
                model.addPractice(new TeacherPracticeItem("练习"));
                adapter.notifyDataSetChanged();
            }
        });
        Button deletePractice_btn = (Button)view.findViewById(R.id.delete_practice_btn);
        deletePractice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 选择一个练习进行删除
                model.deletePractice("");
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void addPractice(TeacherPracticeItem item){
        // TODO: 实现
    }
}
