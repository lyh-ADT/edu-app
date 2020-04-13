package com.edu_app.controller.teacher.course;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.teacher.TeacherInfo;
import com.edu_app.model.teacher.course.CoursePage;
import com.edu_app.view.teacher.Fragment;

public class CoursePageController extends Controller {
    private final int CAMERA_PERMISSION = 0;
    private final int MIC_PERMISSION = 1;
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
                // 检查录音机权限
                if(ContextCompat.checkSelfPermission(fragment.getActivity().getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(fragment.getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, MIC_PERMISSION);
                    return;
                }
                FragmentManager manager = fragment.getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, com.edu_app.view.teacher.Fragment.newInstance("live_stream", info));
                transaction.addToBackStack(null);
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
        } else if(requestCode == MIC_PERMISSION){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                view.findViewById(R.id.start_live_stream_btn).performClick();
            }else{
                Toast.makeText(fragment.getActivity().getApplicationContext(), "没有麦克风权限，无法直播", Toast.LENGTH_LONG).show();
            }
        }
    }
}
