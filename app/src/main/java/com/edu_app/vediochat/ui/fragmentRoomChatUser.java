package com.edu_app.vediochat.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.edu_app.R;
import com.edu_app.controller.student.course.CourseMainController;

public class fragmentRoomChatUser extends Fragment {
    private final ChatSingleActivity activity;

    public fragmentRoomChatUser(ChatSingleActivity activity){
        this.activity = activity;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stu_course_roomchatuser,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvTeacher = getActivity().findViewById(R.id.coursePage_roomChat_tvTeacher);
        TextView tvTitle = getActivity().findViewById(R.id.coursePage_roomChat_tvTitle);
        TextView tvStartTime = getActivity().findViewById(R.id.coursePage_roomChat_tvStartTime);
        activity.setCourseInfoView(tvTeacher,tvTitle,tvStartTime);
    }
}
