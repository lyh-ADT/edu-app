package com.edu_app.view.student.pracitce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;

import com.edu_app.R;

public class activityDoExam extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_practice_doexam);
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode==KeyEvent.KEYCODE_BACK){
//            Intent intent = new Intent();
//            intent.setClass(activityDoExam.this,activitySubjectMath.class);
//            startActivity(intent);
//            activityDoExam.this.finish();
//        }
//        return true;
//    }
}
