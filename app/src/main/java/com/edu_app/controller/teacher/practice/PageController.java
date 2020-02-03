package com.edu_app.controller.teacher.practice;

import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.edu_app.R;
import com.edu_app.model.teacher.TeacherInfo;
import com.edu_app.model.teacher.practice.PracticePage;
import com.edu_app.model.teacher.practice.PracticeItem;
import com.edu_app.view.teacher.Fragment;

import java.util.List;

public class PageController {
    private androidx.fragment.app.Fragment fragment;
    private PracticePage model;
    private PracticeListAdapter practiceListAdapter;

    public PageController(androidx.fragment.app.Fragment fragment, View view, TeacherInfo teacherInfo){
        this.fragment = fragment;
        model = new PracticePage(teacherInfo, this);
        practiceListAdapter = new PracticeListAdapter(fragment.getLayoutInflater(), model);
        bindListener(view);
    }

    public void notifyDataChanged(){
        practiceListAdapter.notifyDataSetChanged();
    }


    public void error(String message){
        Looper.prepare();
        Toast.makeText(fragment.getContext(), message, Toast.LENGTH_LONG).show();
        Looper.loop();
    }

    private void bindListener(View view){
        ListView practice_list = (ListView)view.findViewById(R.id.practice_list);

        practice_list.setAdapter(practiceListAdapter);

        Button addPractice_btn = (Button)view.findViewById(R.id.add_practice_btn);
        addPractice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = fragment.requireFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, Fragment.newInstance("add_practice", null));
                transaction.commit();
            }
        });
        final Button deletePractice_btn = (Button)view.findViewById(R.id.delete_practice_btn);
        deletePractice_btn.setOnClickListener(new View.OnClickListener() {
            private boolean deleteMode = false;
            @Override
            public void onClick(View v) {
                if(deleteMode){
                    // 切换回详情
                    deleteMode = false;
                    PracticeItemController.setDeleteMode(false);
                    deletePractice_btn.setText(R.string.delete_practice_text);
                } else {
                    // 进入删除模式
                    deleteMode = true;
                    PracticeItemController.setDeleteMode(true);
                    deletePractice_btn.setText(R.string.cancel_text);
                }
            }
        });
    }
}
