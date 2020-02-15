package com.edu_app.controller.student.person;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.edu_app.R;

public class PersonCourseController implements View.OnClickListener {
    private final AppCompatActivity activity;
    private Button addBt;
    private Button getBt;

    public PersonCourseController(AppCompatActivity activity) {
        this.activity = activity;

    }

    public void setOnClick() {
        getBt = (Button)activity.findViewById(R.id.personPage_stuClass_getClassBt);
        getBt.setOnClickListener(this);
        addBt = (Button)activity.findViewById(R.id.personPage_stuClass_addClassBt);
        addBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personPage_stuClass_addClassBt:
               String classId = ((EditText)this.activity.findViewById(R.id.personPage_stuClass_addClassText)).getText().toString();
               postClassId(classId);
               break;
            case R.id.personPage_stuClass_getClassBt:

                String courseName = getCourseName();
                ((TextView)this.activity.findViewById(R.id.personPage_stuClass_getClassText)).setText(courseName);
                break;

        }
    }

    private String getCourseName() {
//        TODO:从服务器获取课程名字
        return "";
    }

    private void postClassId(String classId) {
//        TODO:上传班级代码，添加到个人信息中去
    }
}
