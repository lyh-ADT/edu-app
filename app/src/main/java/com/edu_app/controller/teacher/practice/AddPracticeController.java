package com.edu_app.controller.teacher.practice;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.edu_app.R;
import com.edu_app.model.Question;
import com.edu_app.model.teacher.TeacherInfo;
import com.edu_app.model.teacher.practice.AddPractice;
import com.edu_app.view.teacher.Fragment;

public class AddPracticeController {
    private androidx.fragment.app.Fragment fragment;
    private AddPractice model;

    public AddPracticeController(androidx.fragment.app.Fragment fragment, View view, TeacherInfo teacherInfo){
        this.fragment = fragment;
        model = new AddPractice(teacherInfo);
        bindListener(view);
    }

    private void bindListener(final View view){
        ListView practice_list = view.findViewById(R.id.practice_list);
        final AddPracticeListAdapter adapter = new AddPracticeListAdapter(fragment.getLayoutInflater(), model);
        practice_list.setAdapter(adapter);

        Button addQuestion_btn = view.findViewById(R.id.add_question_btn);
        addQuestion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = fragment.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_add_question, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(fragment.requireContext());
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
                                    Toast.makeText(fragment.getContext(), "问题不能为空", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                Question question = new Question();
                                question.setQuestion(question_string);
                                question.setOrderNumber(model.getQuestionCount()+1);
                                model.addQuestion(question);
                                adapter.notifyDataSetChanged();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        Button exit_btn = view.findViewById(R.id.exit_btn);
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(fragment.requireContext());
                builder.setMessage("操作不会被保存")
                        .setTitle("确认退出")
                        .setCancelable(true)
                        .setPositiveButton("取消", null)
                        .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FragmentManager manager = fragment.requireFragmentManager();
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
}
