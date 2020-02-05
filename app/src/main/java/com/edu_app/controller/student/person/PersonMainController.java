package com.edu_app.controller.student.person;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.View;

import com.edu_app.R;
import com.edu_app.view.student.person.activityContactService;
import com.edu_app.view.student.person.activityHistoryDownload;
import com.edu_app.view.student.person.activityPersonCourse;
import com.edu_app.view.student.person.activityPersonInfo;

public class PersonMainController implements View.OnClickListener {
    private Activity activity;

    public PersonMainController(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.personPage_stuInfo:
                intent.setClass(activity,activityPersonInfo.class);
                break;
            case R.id.personPage_stuCourse:
                intent.setClass(activity,activityPersonCourse.class);
                break;
            case R.id.personPage_stuDownload:
                intent.setClass(activity,activityHistoryDownload.class);

                break;
            case R.id.personPage_contactCS:

                intent.setClass(activity,activityContactService.class);
                break;
            default:
                break;
        }
        activity.startActivity(intent);
        activity.finish();

    }
}
