package com.edu_app.view.student.pracitce;


import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.edu_app.R;
import com.edu_app.controller.student.practice.DoExamController;

public class activityDoExam extends AppCompatActivity {
    private DoExamController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_stu_practice_doexam);
        controller = new DoExamController(this);
        controller.setView();
        controller.setSubmitListener();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            controller.setKeyDown();
        }
        return true;
    }
}
