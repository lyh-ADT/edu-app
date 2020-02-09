package com.edu_app.controller.teacher.practice;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.teacher.TeacherInfo;
import com.edu_app.model.teacher.practice.PracticeItem;
import com.edu_app.model.teacher.practice.StudentPractice;
import com.edu_app.view.teacher.Fragment;

public class StudentListController extends Controller {
    private Fragment fragment;
    private TeacherInfo teacherInfo;
    private StudentPractice model;
    private Callback callback;
    private PracticeItem practiceItem;
    private ListAdapter listAdapter;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if(msg.what == 0){
                listAdapter.notifyDataSetChanged();
            }
            return true;
        }
    });

    public void error(String e) {
        Looper.prepare();
        Toast.makeText(fragment.getContext(), e, Toast.LENGTH_LONG).show();
        Looper.loop();
    }

    public interface Callback extends Controller.Callback{
        PracticeItem getPracticeItem();
        void show();
    }

    @Override
    public void bindCallback(Controller.Callback callback){
        this.callback = (Callback)callback;
        this.practiceItem = this.callback.getPracticeItem();
        model.setController(this);
        setValues();
        model.getPracticeList();
    }

    public StudentListController(View view, Fragment fragment, TeacherInfo teacherInfo) {
        super(view, new StudentPractice(teacherInfo));
        this.fragment = fragment;
        this.model = (StudentPractice) super.model;
        this.teacherInfo =teacherInfo;
        bindListener();
        setFullScreen(fragment.requireActivity());
    }

    public PracticeItem getPracticeItem(){
        return practiceItem;
    }

    public void notifyDataSetChanged(){
        handler.sendEmptyMessage(0);
    }

    @Override
    protected void bindListener(){
        ListView list = view.findViewById(R.id.student_list);
        listAdapter = new ListAdapter();
        list.setAdapter(listAdapter);

        Button exit_btn = view.findViewById(R.id.exit_btn);
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
                transaction.remove(fragment);
                transaction.commit();
                unSetFullScreen(fragment.requireActivity());
                callback.show();
            }
        });
    }

    private class ListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return model.getPracticeCount();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(position > -1){
                convertView = new TextView(view.getContext());
                ((TextView)convertView).setText(model.getAuthorNameAt(position));
            }
            return convertView;
        }
    }
}
