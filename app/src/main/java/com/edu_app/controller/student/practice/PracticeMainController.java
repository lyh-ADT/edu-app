package com.edu_app.controller.student.practice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.edu_app.R;
import com.edu_app.model.Practice;
import com.edu_app.view.student.pracitce.activitySubjectChinese;
import com.edu_app.view.student.pracitce.activitySubjectEnglish;
import com.edu_app.view.student.pracitce.activitySubjectMath;
import java.util.ArrayList;

public class PracticeMainController implements View.OnClickListener {
    private FragmentActivity activity;
    private ArrayList<Practice> practicelist;
    public PracticeMainController(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        setData();
        switch (v.getId()) {
            case R.id.practicePage_subject_chinese:
                intent.setClass(activity, activitySubjectChinese.class);
                break;
            case R.id.practicePage_subject_math:
                intent.setClass(activity, activitySubjectMath.class);
                break;
            case R.id.practicePage_subject_english:
                intent.setClass(activity, activitySubjectEnglish.class);
                break;
            default:
                break;
        }
//  TODO  此处传递所有的练习到某一科目的页面



        Bundle bundle = new Bundle();
        bundle.putSerializable("alldata",practicelist);
        intent.putExtras(bundle);
        activity.startActivity(intent);
//        activity.finish();

    }
    /**
     *设置所获取的试题（某一套）
     *
     *
     */

    public void setData(){
        practicelist = new ArrayList<Practice>();
        for (int i=0;i<50;++i){
            String title = "第"+i+"套试卷";
            Practice p = new Practice(title);
            practicelist.add(p);
        }
    }
}
