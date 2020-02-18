package com.edu_app.model.teacher;

import android.content.Context;
import android.content.SharedPreferences;

import com.edu_app.model.UserInfo;

public class TeacherInfo extends UserInfo {
    private String uid;

    public TeacherInfo(Context context){
        SharedPreferences pref = context.getSharedPreferences("uid", Context.MODE_PRIVATE);
        uid = pref.getString("uid", "empty");
    }

    public String getUID(){
        return uid;
    }
    public String getHost(){
        return "http://139.159.176.78:2000";
    }
}
