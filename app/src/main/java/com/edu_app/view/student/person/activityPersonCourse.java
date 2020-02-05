package com.edu_app.view.student.person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;


import androidx.annotation.Nullable;

import com.edu_app.R;
import com.edu_app.view.activityFunction;

public class activityPersonCourse extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_person_course);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            intent.setClass(activityPersonCourse.this, activityFunction.class);
            startActivity(intent);
            activityPersonCourse.this.finish();

        }
        return true;
    }
}
