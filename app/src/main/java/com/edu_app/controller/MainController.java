package com.edu_app.controller;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.edu_app.view.student.activityStuFunction;
import com.edu_app.view.teacher.activityTeaFunction;

import java.io.File;
/**
 * 此控制器用于应用刚刚启动应该跳转的页面，对应的活动界面为activityMain
 *
 */
public class MainController {
    private Context context;
    public String uid;
    public String accountType = "student";
    public MainController(Context context){
        this.context=context;
        uidIsValid();
    }
    //  判断本地是否有这个文件
    public Boolean existUidFile(){
        File file = new File("uid");
        return file.exists();
    }
    //  判断uid是否有效
    public boolean uidIsValid(){
//        TODO:    首先判断本地是否存在uid文件
//                 如果存在则从服务器判断是否过期,过期返回false，否则保存uid并返回true
//                 如果不存在则返回false
//        注：保存uid
        SharedPreferences pref = context.getSharedPreferences("uid", Context.MODE_PRIVATE);
        String uid = pref.getString("uid","");
        String accountType = pref.getString("accountType", "student");
        this.accountType = accountType;
        this.uid=uid;
        return true;
    }
    public void jumpToRightActivity() {
//        TODO: 如果uid有效，从uid识别用户身份
//                          老师：启动activityTeaFunction
//                          学生：启动activityStuFunction
//              uid无效，启动登录活动LoginActivity
        Intent intent = new Intent();
        if("student".equals(accountType)){
            intent.setClass(this.context, activityStuFunction.class);
        } else {
            intent.setClass(this.context, activityTeaFunction.class);
        }

        context.startActivity(intent);
        ((Activity)context).finish();
    }
}

