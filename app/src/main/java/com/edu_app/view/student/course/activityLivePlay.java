package com.edu_app.view.student.course;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.edu_app.R;
import com.edu_app.controller.student.course.CourseLiveController;

/**
 * 用于直播页面活动
 */
public class activityLivePlay extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_course_live_play);
        CourseLiveController controller = new CourseLiveController(this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int orientation = getResources().getConfiguration().orientation;
        if (keyCode == KeyEvent.KEYCODE_BACK && orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast toast = Toast.makeText(this,"请在竖屏下返回",Toast.LENGTH_LONG);
            LinearLayout layout = (LinearLayout) toast.getView();
            TextView tv = (TextView) layout.getChildAt(0);
            tv.setTextSize(18);
            tv.setTextColor(Color.RED);
            toast.show();
            return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
        }else{
            if (keyCode == KeyEvent.KEYCODE_BACK && orientation == Configuration.ORIENTATION_PORTRAIT) {
                {
                    activityLivePlay.this.finish();

                }

            }
        }
        return true;

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

    }


}