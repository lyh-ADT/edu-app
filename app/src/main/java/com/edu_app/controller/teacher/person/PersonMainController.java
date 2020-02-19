package com.edu_app.controller.teacher.person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.edu_app.R;
import com.edu_app.view.teacher.person.activityContactService;
import com.edu_app.view.teacher.person.activityPersonCourse;
import com.edu_app.view.teacher.person.activityPersonInfo;

public class PersonMainController implements View.OnClickListener {
    private Activity activity;

    public PersonMainController(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        Bundle bundle = this.activity.getIntent().getExtras();
        intent.putExtras(bundle);
        switch (v.getId()) {
            case R.id.personPage_stuInfo:
                intent.setClass(activity,activityPersonInfo.class);
                break;
            case R.id.personPage_stuCourse:
                intent.setClass(activity,activityPersonCourse.class);
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
