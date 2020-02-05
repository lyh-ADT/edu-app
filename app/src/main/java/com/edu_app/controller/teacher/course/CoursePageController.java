package com.edu_app.controller.teacher.course;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.teacher.TeacherInfo;
import com.edu_app.model.teacher.course.CoursePage;

public class CoursePageController extends Controller {
    private final int CAMERA_PERMISSION = 0;
    private Fragment fragment;
    private TeacherInfo info;

    public CoursePageController(Fragment fragment, View view, TeacherInfo info) {
        super(view, new CoursePage());
        this.fragment = fragment;
        this.info = info;
        bindListener();
    }

    @Override
    protected void bindListener(){
        Button startLiveStream_btn = view.findViewById(R.id.start_live_stream_btn);
        startLiveStream_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 检查相机权限
                if(ContextCompat.checkSelfPermission(fragment.getActivity().getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(fragment.getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
                    return;
                }
                FragmentManager manager = fragment.getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, com.edu_app.view.teacher.Fragment.newInstance("live_stream", info));
                transaction.commit();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERMISSION){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                view.findViewById(R.id.start_live_stream_btn).performClick();
            }else{
                Toast.makeText(fragment.getActivity().getApplicationContext(), "没有相机权限，无法直播", Toast.LENGTH_LONG).show();
            }
        }
    }
}
