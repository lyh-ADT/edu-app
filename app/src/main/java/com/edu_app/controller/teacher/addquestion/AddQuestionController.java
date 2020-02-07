package com.edu_app.controller.teacher.addquestion;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.teacher.addquestion.QuestionItemFactory;
import com.edu_app.model.teacher.practice.QuestionItem;
import com.edu_app.view.teacher.AddQuestionInfoFragment;

public class AddQuestionController extends Controller {
    private int SELECT_PIC = 0;
    private QuestionItem question;
    private Fragment fragment;
    private String questionType;

    public AddQuestionController(View view, Fragment fragment) {
        super(view, null);
        this.fragment = fragment;
        bindListener();
        setFullScreen();
    }


    @Override
    protected void bindListener(){
        Spinner question_type = view.findViewById(R.id.question_type_sn);
        question_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] questionType = view.getContext().getResources().getStringArray(R.array.question_type);
                AddQuestionController.this.questionType = questionType[position];
                changeToQuestionType();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Button addQuestion_btn = view.findViewById(R.id.add_question_btn);
        addQuestion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(question.getQuestion() == null || question.getAnswer() == null){
                    Toast.makeText(view.getContext(), "信息不完整", Toast.LENGTH_LONG).show();
                    return;
                }
                fragment.getFragmentManager().popBackStack();
                unSetFullScreen();
            }
        });

        Button cancel_btn = view.findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("添加选项")
                        .setMessage("题目将不会保存")
                        .setCancelable(true)
                        .setPositiveButton("取消", null)
                        .setNegativeButton("不保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                question.setQuestion(null);
                                fragment.getFragmentManager().popBackStack();
                                unSetFullScreen();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void setFullScreen(){
        Activity activity = fragment.getActivity();
        // 关闭标题栏
        ActionBar actionBar = activity.getActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        // 关闭底部的导航栏
        View navigation_bar = activity.findViewById(R.id.navigation_bar);
        navigation_bar.setVisibility(View.GONE);
    }

    private void unSetFullScreen(){
        Activity activity = fragment.getActivity();
        // 关闭标题栏
        ActionBar actionBar = activity.getActionBar();
        if(actionBar != null){
            actionBar.show();
        }
        // 关闭底部的导航栏
        View navigation_bar = activity.findViewById(R.id.navigation_bar);
        navigation_bar.setVisibility(View.VISIBLE);
    }

    private void changeToQuestionType() {
        question = QuestionItemFactory.newInstance(questionType, null);
        FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
        transaction.add(R.id.question_info, AddQuestionInfoFragment.newInstance(question));
        transaction.commit();
    }
}
