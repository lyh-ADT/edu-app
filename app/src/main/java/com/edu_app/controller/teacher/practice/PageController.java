package com.edu_app.controller.teacher.practice;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.Practice;
import com.edu_app.model.teacher.TeacherInfo;
import com.edu_app.model.teacher.practice.PracticeItem;
import com.edu_app.model.teacher.practice.PracticePage;
import com.edu_app.view.teacher.Fragment;

public class PageController extends Controller {
    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    // 练习列表数据改变
                    practiceListAdapter.notifyDataSetChanged();
                    return true;
                default:
                    return false;
            }
        }
    });

    private android.app.Fragment fragment;
    private PracticePage model;
    private PracticeListAdapter practiceListAdapter;
    private TeacherInfo teacherInfo;

    public PageController(android.app.Fragment fragment, View view, TeacherInfo teacherInfo){
        super(view, new PracticePage(teacherInfo));
        this.fragment = fragment;
        this.teacherInfo = teacherInfo;
        model = (PracticePage)super.model;
        model.setController(this);
        practiceListAdapter = new PracticeListAdapter(fragment.getActivity().getLayoutInflater(), model);
        bindListener();
    }

    public void error(String message){
        Looper.prepare();
        // TODO: 加一个判断fragment是否因为被replace而为空的判断
        Toast.makeText(fragment.getView().getContext(), message, Toast.LENGTH_LONG).show();
        Looper.loop();
    }

    @Override
    protected void bindListener(){
        ListView practice_list = view.findViewById(R.id.practice_list);

        practice_list.setAdapter(practiceListAdapter);

        Button addPractice_btn = view.findViewById(R.id.add_practice_btn);
        addPractice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = fragment.getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, Fragment.newInstance("practice_info", teacherInfo, new PracticeInfoController.Callback() {
                    @Override
                    public PracticeItem getPractice() {
                        return new PracticeItem(new Practice());
                    }

                    @Override
                    public void addPractice(PracticeItem practiceItem) {
                        model.addPractice(practiceItem);
                    }

                    @Override
                    public boolean editable() {
                        return true;
                    }
                }));
                transaction.commit();
            }
        });
        final Button deletePractice_btn = view.findViewById(R.id.delete_practice_btn);
        deletePractice_btn.setOnClickListener(new View.OnClickListener() {
            private boolean deleteMode = false;
            @Override
            public void onClick(View v) {
                if(deleteMode){
                    // 切换回详情
                    deleteMode = false;
                    PracticeItemController.setDeleteMode(false);
                    deletePractice_btn.setText(R.string.delete_practice_text);
                } else {
                    // 进入删除模式
                    deleteMode = true;
                    PracticeItemController.setDeleteMode(true);
                    deletePractice_btn.setText(R.string.cancel_text);
                }
            }
        });
    }
}
