package com.edu_app.view.student.course;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;




import com.edu_app.R;
import com.edu_app.controller.student.course.CourseMainController;
import com.edu_app.controller.student.course.CourserFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class fragmentCourse extends Fragment {
    private TabLayout tablayout;
    private ViewPager viewpager;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> tab_titles;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        设置页面启动使得默认布局
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.coursePage_course_viewpager,new fragmentCourseLive());
        ft.commit();
        tablayout = getActivity().findViewById(R.id.coursePage_course_tab);
        viewpager = getActivity().findViewById(R.id.coursePage_course_viewpager);
        CourseMainController controller = new CourseMainController(this);
        fragments = controller.setAllPageFragment();
        tab_titles = controller.getTabTitle();
        CourserFragmentAdapter adapter = new CourserFragmentAdapter(getFragmentManager(),fragments,tab_titles);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
    }


}
