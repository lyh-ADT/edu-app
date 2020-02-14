package com.edu_app.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;

import com.edu_app.R;
import com.edu_app.controller.MainController;
import com.edu_app.model.teacher.TeacherInfo;
import com.edu_app.view.student.course.fragmentCourse;
import com.edu_app.view.student.person.fragmentPerson;
import com.edu_app.view.student.pracitce.fragmentPractice;


public class activityFunction extends AppCompatActivity implements View.OnClickListener {
    private Bundle uidbundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        ImageView img1 = findViewById(R.id.bar_imgPractice);
        ImageView img2 = findViewById(R.id.bar_imgCourse);
        ImageView img3 = findViewById(R.id.bar_imgPersonInfo);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        MainController mainController = new MainController(this);
//        设置打开页面时的默认界面
        img3.performClick();
//        如果有效就携带uid，不然就去登录活动
//        if (mainController.uidIsRight()) {
//            uidbundle = mainController.getUidBundle();
//        } else {
//            Intent intent = new Intent(activityFunction.this, LoginActivity.class);
//            startActivity(intent);
//        }
    }

    @Override
    public void onClick(View v) {
        androidx.fragment.app.FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment f;
        Intent uidintent;
        boolean isTeacher = false;
        if(isTeacher){
            switch (v.getId()) {
                case R.id.bar_imgPractice:
                    f = com.edu_app.view.teacher.Fragment.newInstance("practice", new TeacherInfo());
                    break;

                case R.id.bar_imgCourse:
                    f = com.edu_app.view.teacher.Fragment.newInstance("course", new TeacherInfo());
                    break;

                case R.id.bar_imgPersonInfo:
                    f = new fragmentPerson();
                    uidintent = new Intent(activityFunction.this, fragmentPractice.class);
                    break;
                default:
                    f = null;
                    break;
            }
            ft.replace(R.id.main_fragment, f);
            ft.commit();
        }else{
            switch (v.getId()) {
                case R.id.bar_imgPractice:
                    f = new fragmentPractice();
                    uidintent = new Intent(activityFunction.this, fragmentPractice.class);
                    break;

                case R.id.bar_imgCourse:
                    f = new fragmentCourse();
                    uidintent = new Intent(activityFunction.this, fragmentPractice.class);
                    break;

                case R.id.bar_imgPersonInfo:
                    f = new fragmentPerson();
                    uidintent = new Intent(activityFunction.this, fragmentPractice.class);
                    break;
                default:
                    f = null;
                    uidintent = new Intent();
                    break;
            }
            ft.replace(R.id.main_fragment, f);
            ft.commit();
        }
    }
}
