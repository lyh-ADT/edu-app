package com.edu_app.view;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.app.Fragment;

import com.edu_app.R;
import com.edu_app.controller.MainController;


public class activityFunction extends Activity implements View.OnClickListener {
    private Bundle uidbundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuction);
//        设置打开页面时的默认界面
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.function_fragment, new fragmentPersonInfo());
        ft.commit();
        ImageView img1 = (ImageView) findViewById(R.id.bar_imgPractice);
        ImageView img2 = (ImageView) findViewById(R.id.bar_imgCourse);
        ImageView img3 = (ImageView) findViewById(R.id.bar_imgPersonInfo);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        MainController mainController = new MainController(this);
//        如果有效就携带uid，不然就去登录活动
        if (mainController.uidIsRight()) {
            uidbundle = mainController.getUidBundle();
        } else {
            Intent intent = new Intent(activityFunction.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
//        androidx.fragment.app.FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment f;
        Intent uidintent;

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
                f = new fragmentPersonInfo();
                uidintent = new Intent(activityFunction.this, fragmentPractice.class);
                break;
            default:
                f = null;
                uidintent = new Intent();
                break;
        }
        uidintent.putExtras(uidbundle);
        startActivity(uidintent);
        ft.replace(R.id.function_fragment, f);
        ft.commit();
    }
}
