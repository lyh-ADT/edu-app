package com.edu_app.controller.teacher.practice;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.Practice;
import com.edu_app.model.teacher.TeacherInfo;
import com.edu_app.model.teacher.practice.PracticeItem;
import com.edu_app.model.teacher.practice.PracticePage;
import com.edu_app.view.teacher.Fragment;

import java.util.ArrayList;

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

    private Fragment fragment;
    private PracticePage model;
    private PracticeListAdapter practiceListAdapter;
    private TeacherInfo teacherInfo;
    private boolean deleteMode = false;

    public PageController(Fragment fragment, View view, TeacherInfo teacherInfo){
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
        if(fragment == null || fragment.getView() == null){
            return;
        }
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
                transaction.add(R.id.main_fragment, Fragment.newInstance("practice_info", teacherInfo, new PracticeInfoController.Callback() {
                    @Override
                    public PracticeItem getPractice() {
                        return new PracticeItem(new Practice());
                    }

                    @Override
                    public boolean editable() {
                        return true;
                    }

                    @Override
                    public void show() {
                        FragmentManager manager = fragment.getFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.show(fragment);
                        transaction.commit();
                        model.getPracticeList();
                    }
                }));
                transaction.hide(fragment);
                transaction.commit();
            }
        });
        final Button deletePractice_btn = view.findViewById(R.id.delete_practice_btn);
        deletePractice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deleteMode){
                    // 切换回详情
                    deleteMode = false;
                    deletePractice_btn.setText(R.string.delete_practice_text);
                } else {
                    // 进入删除模式
                    deleteMode = true;
                    deletePractice_btn.setText(R.string.cancel_text);
                }
            }
        });
    }

    private class PracticeListAdapter extends BaseAdapter {
        private final PracticePage model;
        private LayoutInflater inflater;

        public PracticeListAdapter(LayoutInflater inflater, PracticePage model){
            this.inflater = inflater;
            this.model = model;
        }

        @Override
        public int getCount() {
            return model.getPracticeItemCount();
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
            if(position >= -1){
                convertView = inflater.inflate(R.layout.fragment_teacher_practice_item, parent, false);
                new ListItemController(convertView, model.getPracticeItemAt(position));
            }
            return convertView;
        }
    }

    private class ListItemController extends Controller {
        private View.OnClickListener clickItemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deleteMode){
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("删除"+model.getTitle())
                            .setTitle("确认删除")
                            .setCancelable(true)
                            .setPositiveButton("取消", null)
                            .setNegativeButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    PageController.this.model.deletePractice(model);
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    FragmentManager manager = fragment.getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.add(R.id.main_fragment, Fragment.newInstance("practice_info", teacherInfo, new PracticeInfoController.Callback() {
                        @Override
                        public PracticeItem getPractice() {
                            return model;
                        }

                        @Override
                        public boolean editable() {
                            return false;
                        }

                        @Override
                        public void show() {
                            FragmentManager manager = fragment.getFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.show(fragment);
                            transaction.commit();
                        }
                    }));
                    transaction.hide(fragment);
                    transaction.commit();
                }
            }
        };
        private final PracticeItem model;
        private View view;


        public ListItemController(View view, PracticeItem model){
            super(view, model);
            this.view = view;
            this.model = model;

            setValues();
            bindListener();
        }

        @Override
        protected void bindListener(){
            view.setOnClickListener(clickItemListener);

            Button judge_btn = view.findViewById(R.id.judge);
            judge_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = fragment.getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.add(R.id.main_fragment, Fragment.newInstance("student_practice_info", teacherInfo, new StudentListController.Callback() {
                        @Override
                        public PracticeItem getPracticeItem() {
                            return model;
                        }

                        @Override
                        public void show(){
                            FragmentManager manager = fragment.getFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.show(fragment);
                            transaction.commit();
                        }
                    }));
                    transaction.hide(fragment);
                    transaction.commit();
                }
            });
        }
    }
}
