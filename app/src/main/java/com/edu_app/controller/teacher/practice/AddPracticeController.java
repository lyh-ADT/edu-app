package com.edu_app.controller.teacher.practice;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.teacher.practice.PictureQuestionItem;
import com.edu_app.model.teacher.TeacherInfo;
import com.edu_app.model.teacher.practice.AddPractice;
import com.edu_app.model.teacher.practice.QuestionItem;
import com.edu_app.view.teacher.Fragment;

public class AddPracticeController extends Controller {
    private final int SELECT_PIC = 0;
    private android.app.Fragment fragment;
    private AddPractice model;
    private AddPracticeListAdapter practiceListAdapter;
    private Dialog dialog;

    public AddPracticeController(android.app.Fragment fragment, View view, TeacherInfo teacherInfo){
        super(view, new AddPractice(teacherInfo));
        this.fragment = fragment;
        model = (AddPractice)super.model;
        bindListener();
    }

    @Override
    protected void bindListener(){
        ListView practice_list = view.findViewById(R.id.practice_list);
        practiceListAdapter = new AddPracticeListAdapter(fragment.getActivity().getLayoutInflater(), model);
        practice_list.setAdapter(practiceListAdapter);

        Button addQuestion_btn = view.findViewById(R.id.add_question_btn);
        addQuestion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = fragment.getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_add_question, null);
                Button addPicture_btn = dialogView.findViewById(R.id.add_picture_btn);
                addPicture_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        fragment.startActivityForResult(intent, SELECT_PIC);
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getView().getContext());
                builder.setTitle("添加题目")
                        .setCancelable(true)
                        .setView(dialogView)
                        .setPositiveButton("取消", null)
                        .setNegativeButton("添加", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TextView question_edit = dialogView.findViewById(R.id.input_question_edit);
                                String question_string = question_edit.getText().toString();
                                if(question_string.length() <= 0){
                                    Toast.makeText(fragment.getView().getContext(), "问题不能为空", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                QuestionItem question = new QuestionItem();
                                question.setQuestion(question_string);
                                question.setOrderNumber(model.getQuestionCount()+1);
                                model.addQuestion(question);
                                practiceListAdapter.notifyDataSetChanged();
                            }
                        });

                dialog = builder.create();
                dialog.show();
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
                Log.e("AddPracticeController", "onActivityResult获取图片data为空");
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
}
