package com.edu_app.view.student.pracitce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.edu_app.R;
import com.edu_app.model.Practice;

public class activityLookExam extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_practice_lookexam);
        TextView text = findViewById(R.id.practicePage_practice_lookexam_title);
        Practice practice = (Practice) getIntent().getSerializableExtra("data");
        text.setText(practice.getTitle());

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode==KeyEvent.KEYCODE_BACK){
//            Intent intent = new Intent();
//            intent.setClass(activityLookExam.this,activitySubjectMath.class);
//            startActivity(intent);
//            activityLookExam.this.finish();
//        }
//        return true;
//    }
}
