package com.edu_app.view.student.course;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.edu_app.R;
import com.edu_app.controller.student.course.CourseLiveController;

/**
 * 用于直播页面活动
 */
public class activityLivePlay extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_course_live_play);
        Button bt_start = findViewById(R.id.coursePage_live_bt_start);
        CourseLiveController controller = new CourseLiveController(this);
        bt_start.setOnClickListener(controller);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_BACK){
            activityLivePlay.this.finish();
        }

        return true;
    }

}