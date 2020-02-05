package com.edu_app.view.student.person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;


import androidx.annotation.Nullable;

import com.edu_app.R;
import com.edu_app.view.activityFunction;

public class activityContactService extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_person_contactservice);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            intent.setClass(activityContactService.this, activityFunction.class);
            startActivity(intent);
            activityContactService.this.finish();

        }
        return true;
    }

}
