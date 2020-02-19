package com.edu_app.view.student.course;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.edu_app.R;
import com.edu_app.controller.student.course.CourseMainController;

/**
 * 用于直播碎片
 */
public class fragmentCourseLive extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stu_course_live,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CourseMainController controller = new CourseMainController(this);
        Button bt_start = view.findViewById(R.id.coursePage_live_bt_palyStart);
        bt_start.setOnClickListener(controller);
    }
}
