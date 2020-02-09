package com.edu_app.controller.student.practice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.edu_app.model.Practice;
import com.edu_app.view.student.pracitce.activityDoExam;
import com.edu_app.view.student.pracitce.activityLookExam;

/**
 *
 * 控制某个试题是跳转看试题，还是做试题
 */
public class BrowseExercisesController implements View.OnClickListener {
    private AppCompatActivity activity;
    private Practice practice;
    public BrowseExercisesController(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
//        TODO:判断这次测试题有没有做过，如果做过就跳转查看试题活动，否则进入做题活动,根据practice的done属性
//         并且传递具体哪一套试卷
        Intent intent = activity.getIntent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",practice);
        intent.putExtras(bundle);
        if (true) {
            activity.startActivity(intent.setClass(activity, activityLookExam.class));
        } else {
            activity.startActivity(intent.setClass(activity, activityDoExam.class));
        }
    }
    public void setData(Practice practice){
        this.practice = practice;
    }
}
