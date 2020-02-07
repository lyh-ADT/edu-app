package com.edu_app.controller.student.course;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.edu_app.R;
import com.edu_app.view.student.course.activityLivePlay;
import com.edu_app.view.student.course.fragmentCourseChinese;
import com.edu_app.view.student.course.fragmentCourseEnglish;
import com.edu_app.view.student.course.fragmentCourseLive;
import com.edu_app.view.student.course.fragmentCourseMath;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * 用于控制课程的总体活动
 */
public class CourseMainController implements View.OnClickListener {
    private ArrayList<Fragment> fragments;
    private ArrayList<String> tab_titles;
    private Fragment fragment;

    public CourseMainController(Fragment fragment) {
        this.fragment = fragment;
    }

    public ArrayList<Fragment> setAllPageFragment() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new fragmentCourseLive());
        fragments.add(new fragmentCourseMath());
        fragments.add(new fragmentCourseChinese());
        fragments.add(new fragmentCourseEnglish());
        return fragments;
    }

    public ArrayList<String> getTabTitle() {
        tab_titles = new ArrayList<String>();
        tab_titles.add(fragment.getString(R.string.coursePage_tab_1));
        tab_titles.add(fragment.getString(R.string.coursePage_tab_2));
        tab_titles.add(fragment.getString(R.string.coursePage_tab_3));
        tab_titles.add(fragment.getString(R.string.coursePage_tab_4));
        return tab_titles;

    }

    public void setTabTitle(TabLayout tablayout) {
        tablayout.addTab(tablayout.newTab().setText(tab_titles.get(0)));
        tablayout.addTab(tablayout.newTab().setText(tab_titles.get(1)));
        tablayout.addTab(tablayout.newTab().setText(tab_titles.get(2)));
        tablayout.addTab(tablayout.newTab().setText(tab_titles.get(3)));
    }

    @Override
    public void onClick(View v) {
        Log.e("error","被点击");

        switch (v.getId()) {
            case R.id.coursePage_live_bt_palyStart:
                fragment.getActivity().startActivity(new Intent(fragment.getActivity(), activityLivePlay.class));
                break;

        }
    }
}
