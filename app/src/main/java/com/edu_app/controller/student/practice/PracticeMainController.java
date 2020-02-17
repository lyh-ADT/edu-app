package com.edu_app.controller.student.practice;

import android.content.Intent;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.edu_app.R;
import com.edu_app.model.Practice;
import com.edu_app.view.student.pracitce.activitySubjectChinese;
import com.edu_app.view.student.pracitce.activitySubjectEnglish;
import com.edu_app.view.student.pracitce.activitySubjectMath;

import java.util.ArrayList;

/**
 *
 * 控制跳转到哪个科目练习
 *
 */
public class PracticeMainController implements View.OnClickListener {
    private FragmentActivity activity;
    private ArrayList<Practice> practicelist;
    public PracticeMainController(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("uid",this.activity.getIntent().getExtras().get("uid").toString());

        switch (v.getId()) {
            case R.id.practicePage_subject_chinese:
                intent.putExtra("subject","语文");
                intent.setClass(activity, activitySubjectChinese.class);
                break;
            case R.id.practicePage_subject_math:
                intent.putExtra("subject","数学");
                intent.setClass(activity, activitySubjectMath.class);
                break;
            case R.id.practicePage_subject_english:
                intent.putExtra("subject","英语");
                intent.setClass(activity, activitySubjectEnglish.class);
                break;
            default:
                break;
        }

        activity.startActivity(intent);

    }

}
