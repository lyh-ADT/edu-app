package com.edu_app.view;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.app.Fragment;

import com.edu_app.R;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentActivity;


public class activityFunction extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuction);
//        设置打开页面时的默认界面
        FragmentTransaction ft =  getFragmentManager().beginTransaction();
        ft.replace(R.id.function_fragment,new fragmentPersonInfo());
        ft.commit();
        ImageView img1 = (ImageView) findViewById(R.id.bar_imgPractice);
        ImageView img2 = (ImageView) findViewById(R.id.bar_imgCourse);
        ImageView img3 = (ImageView) findViewById(R.id.bar_imgPersonInfo);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        androidx.fragment.app.FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
       Fragment f;
        switch (v.getId()) {
            case R.id.bar_imgPractice:
                f = new fragmentPractice();
                break;

            case R.id.bar_imgCourse:
                f = new fragmentCourse();
                break;

            case R.id.bar_imgPersonInfo:
                f = new fragmentPersonInfo();
                break;
            default:
                f=null;
                break;
        }
        ft.replace(R.id.function_fragment, f);
        ft.commit();
    }
}
