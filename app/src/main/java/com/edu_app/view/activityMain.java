package com.edu_app.view;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.edu_app.R;
import com.edu_app.controller.MainController;

public class activityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        获取用户名输入框的内容
        String username = findViewById(R.id.username_edit).getContext().toString();
        MainController mainController = new MainController(username);
//        判断该文件是否存在
        if(mainController.existUidFile()){
            SharedPreferences pref = getSharedPreferences("uid",MODE_PRIVATE);
            String uid = pref.getString("username","");
            mainController.setUid(uid);
//            判断该uid是否有效，如果有效就携带uid与username跳转到功能界面
            if(mainController.uidIsRight()){
                Intent intent = new Intent(activityMain.this,activityFunction.class);
                Bundle bundle = new Bundle();
                bundle.putCharSequence("username",username);
                bundle.putCharSequence("uid",uid);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }else{

        }
    }
}
