package com.edu_app.controller;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;

import com.edu_app.R;
import com.edu_app.view.LoginActivity;
import com.edu_app.view.activityFunction;

import java.io.File;

public class MainController {
    private Context context;
    public String uid;
    public MainController(Context context){
        this.context=context;
    }
    public Boolean existUidFile(){
//        判断本地是否有这个文件
        File file = new File("uid");
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean uidIsRight(){
//        判断该文件是否存在
        if(existUidFile()){
            SharedPreferences pref = context.getSharedPreferences("uid",context.MODE_PRIVATE);
            String uid = pref.getString("uid","");
            this.uid=uid;

//        TODO:    判断该uid是否有效，如果有效就携带uid跳转到个人功能界面
            //        if(uid过期){
//            return false;
//        }
            return true;
        }else{
            return false;
        }
    }
    public Bundle getUidBundle(){
        if(uidIsRight()){
            Bundle bundle = new Bundle();
            bundle.putCharSequence("uid",this.uid);
            return bundle;
        }
        return null;
    }
}

