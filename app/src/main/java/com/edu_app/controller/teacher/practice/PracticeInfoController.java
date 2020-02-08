package com.edu_app.controller.teacher.practice;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.controller.teacher.question.QuestionInfoController;
import com.edu_app.model.teacher.practice.PictureQuestionItem;
import com.edu_app.model.teacher.TeacherInfo;
import com.edu_app.model.teacher.practice.AddPractice;
import com.edu_app.model.teacher.practice.QuestionItem;
import com.edu_app.view.teacher.Fragment;

public class PracticeInfoController extends Controller {
    private final int SELECT_PIC = 0;
    private android.app.Fragment fragment;
    private AddPractice model;
    private ListAdapter practiceListAdapter;
    private Dialog dialog;

    public PracticeInfoController(android.app.Fragment fragment, View view, TeacherInfo teacherInfo){
        super(view, new AddPractice(teacherInfo));
        this.fragment = fragment;
        model = (AddPractice)super.model;
        bindListener();
    }

    @Override
    protected void bindListener(){
        ListView practice_list = view.findViewById(R.id.practice_list);
        practiceListAdapter = new ListAdapter(fragment.getActivity().getLayoutInflater());
        practice_list.setAdapter(practiceListAdapter);

        Button addQuestion_btn = view.findViewById(R.id.add_question_btn);
        addQuestion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
                Fragment questionFragment = Fragment.newInstance("question_info", model.getTeacherInfo(), new QuestionInfoController.Callback() {
                    @Override
                    public void addQuestion(QuestionItem questionItem) {
                        questionItem.setOrderNumber(model.getQuestionCount()+1);
                        model.addQuestion(questionItem);
                        practiceListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void show() {
                        FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
                        transaction.show(fragment);
                        transaction.commit();
                    }

                    @Override
                    public boolean editable(){
                        return true;
                    }

                    @Override
                    public QuestionItem getQuestion() {
                        return null;
                    }
                });
                transaction.hide(fragment);
                transaction.add(R.id.main_fragment, questionFragment);
                transaction.commit();
            }
        });

        Button exit_btn = view.findViewById(R.id.exit_btn);
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getView().getContext());
                builder.setMessage("操作不会被保存")
                        .setTitle("确认退出")
                        .setCancelable(true)
                        .setPositiveButton("取消", null)
                        .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FragmentManager manager = fragment.getFragmentManager();
                                FragmentTransaction transaction = manager.beginTransaction();
                                transaction.replace(R.id.main_fragment, Fragment.newInstance("practice", model.getTeacherInfo()));
                                transaction.commit();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == SELECT_PIC){
            if(data == null){
                Log.e("PracticeInfoController", "onActivityResult获取图片data为空");
                return;
            }
            dialog.dismiss();
            Uri uri = data.getData();
            PictureQuestionItem question = new PictureQuestionItem();
            question.addUri(uri);
            question.setOrderNumber(model.getQuestionCount()+1);
            model.addQuestion(question);
            practiceListAdapter.notifyDataSetChanged();
        }
    }

    private class ListAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        ListAdapter(LayoutInflater inflater){
            this.inflater = inflater;
        }

        @Override
        public int getCount() {
            return model.getQuestionCount();
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
                convertView = inflater.inflate(R.layout.item_question, parent, false);
                new QuestionController(convertView, model.getQuestionAt(position), fragment);
            }
            return convertView;
        }
    }
}
