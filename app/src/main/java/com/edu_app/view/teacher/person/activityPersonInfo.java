package com.edu_app.view.teacher.person;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.edu_app.R;
import com.edu_app.controller.teacher.person.PersonInfoController;
import com.edu_app.view.teacher.activityTeaFunction;

public class activityPersonInfo extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_person_info);
        PersonInfoController controller = new PersonInfoController(this);

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            intent.putExtras(this.getIntent().getExtras());
            intent.setClass(activityPersonInfo.this, activityTeaFunction.class);
            startActivity(intent);
            activityPersonInfo.this.finish();

        }
        return true;
    }
}
