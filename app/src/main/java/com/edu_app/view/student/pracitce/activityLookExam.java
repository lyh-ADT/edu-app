package com.edu_app.view.student.pracitce;


import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.edu_app.controller.student.practice.LookExamController;

public class activityLookExam extends AppCompatActivity {
    private LookExamController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new LookExamController(this);
        controller.setView();


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        controller.setKeyDown();
        return true;
    }
}
