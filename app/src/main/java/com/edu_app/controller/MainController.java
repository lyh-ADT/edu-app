package com.edu_app.controller;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.edu_app.view.LoginActivity;
import com.edu_app.view.activityFunction;


import java.io.File;

public class MainController {
    private Context context;
    public String uid;

    public MainController(Context context) {
        this.context = context;
    }

    public Boolean existUidFile() {
//        判断本地是否有这个文件
        File file = new File("uid");
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean uidNotRight() {
//        判断该文件是否存在
        if (existUidFile()) {
            SharedPreferences pref = context.getSharedPreferences("uid", context.MODE_PRIVATE);
            String uid = pref.getString("uid", "");
            this.uid = uid;

//        TODO:    判断该uid是否有效，如果有效就携带uid跳转到个人功能界面
            //        if(uid过期){
//            return false;
//        }
            return true;
        } else {
            return false;
        }
    }

    public void transmit() {
        //        如果有效就携带uid，不然就去登录活动
        if (uidNotRight()) {
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        } else {
            Bundle bundle = new Bundle();
            Intent uidintent = new Intent(context, activityFunction.class);
            bundle.putCharSequence("uid", this.uid);
            uidintent.putExtras(uidintent);
            context.startActivity(uidintent);
        }

    }

}

