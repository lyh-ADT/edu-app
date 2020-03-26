package com.edu_app.view.student;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.edu_app.R;
import com.edu_app.view.student.course.fragmentCourse;
import com.edu_app.view.student.person.fragmentPerson;
import com.edu_app.view.student.pracitce.fragmentPractice;

public class activityStuFunction extends AppCompatActivity implements View.OnClickListener {
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        bundle = getIntent().getExtras();
//        设置打开页面时的默认界面
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_fragment, new fragmentPerson());
        ft.commit();
        ImageView img1 = findViewById(R.id.bar_imgPractice);
        ImageView img2 = findViewById(R.id.bar_imgCourse);
        ImageView img3 = findViewById(R.id.bar_imgPersonInfo);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        androidx.fragment.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Fragment f;

        switch (v.getId()) {
            case R.id.bar_imgPractice:
                f = new fragmentPractice();
                break;

            case R.id.bar_imgCourse:
                f = new fragmentCourse();
                break;

            case R.id.bar_imgPersonInfo:
                f = new fragmentPerson();
                break;
            default:
                f = null;

                break;
        }

        f.setArguments(bundle);
        ft.replace(R.id.main_fragment, f);
        ft.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            activityStuFunction.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}

