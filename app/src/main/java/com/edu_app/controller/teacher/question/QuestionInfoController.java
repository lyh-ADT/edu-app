package com.edu_app.controller.teacher.question;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.teacher.addquestion.QuestionItemFactory;
import com.edu_app.model.teacher.practice.QuestionItem;
import com.edu_app.view.teacher.QuestionInfoFragment;

import java.util.Arrays;

public class QuestionInfoController extends Controller {
    private int SELECT_PIC = 0;
    private QuestionItem question;
    private Fragment fragment;
    private String questionType;

    public QuestionInfoController(View view, Fragment fragment) {
        super(view, null);
        this.fragment = fragment;
        bindListener();
        setFullScreen();
    }

    private Callback callback;

    public interface Callback extends Controller.Callback{
        void addQuestion(QuestionItem questionItem);
        void show();
        boolean editable();
        QuestionItem getQuestion();
    }

    @Override
    public void bindCallback(Controller.Callback c){
        callback = (Callback) c;
        question = callback.getQuestion();
        setEditable(callback.editable());
    }


    @Override
    protected void bindListener(){
        Spinner question_type = view.findViewById(R.id.question_type_sn);
        question_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] questionType = view.getContext().getResources().getStringArray(R.array.question_type);
                QuestionInfoController.this.questionType = questionType[position];
                changeToQuestionType();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        final EditText question_et = view.findViewById(R.id.input_question_edit);
        question_et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String q = ((EditText)v).getText().toString();
                if(q.length() <= 0){
                    question.setQuestion(null);
                }else{
                    question.setQuestion(q);
                }
                return false;
            }
        });

        Button addQuestion_btn = view.findViewById(R.id.add_question_btn);
        addQuestion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(question.getQuestion() == null || question.getAnswer() == null){
                    Toast.makeText(view.getContext(), "信息不完整", Toast.LENGTH_LONG).show();
                    return;
                }
                callback.addQuestion(question);
                FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
                transaction.remove(fragment);
                transaction.commit();
                unSetFullScreen();
                callback.show();
            }
        });

        Button cancel_btn = view.findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("确认退出")
                        .setMessage("题目将不会保存")
                        .setCancelable(true)
                        .setPositiveButton("取消", null)
                        .setNegativeButton("不保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                question.setQuestion(null);
                                FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
                                transaction.remove(fragment);
                                transaction.commit();
                                unSetFullScreen();
                                callback.show();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void setEditable(boolean editable){
        Spinner question_type = view.findViewById(R.id.question_type_sn);
        final EditText question_et = view.findViewById(R.id.input_question_edit);
        Button addQuestion_btn = view.findViewById(R.id.add_question_btn);

        if(editable){
            question_type.setEnabled(true);
            question_et.setEnabled(true);
            addQuestion_btn.setVisibility(View.VISIBLE);
            bindListener();
            return;
        }
        questionType = question.getQuestionType();
        int index = Arrays.binarySearch(view.getContext().getResources().getStringArray(R.array.question_type), questionType);
        question_type.setSelection(index);
        question_type.setEnabled(false);

        question_et.setEnabled(false);
        question_et.setText(question.getQuestion());

        addQuestion_btn.setVisibility(View.GONE);
        Button cancel_btn = view.findViewById(R.id.cancel_btn);
        cancel_btn.setText(R.string.exit_text);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
                transaction.remove(fragment);
                transaction.commit();
                unSetFullScreen();
                callback.show();
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
        question = QuestionItemFactory.newInstance(questionType, question == null? null : question.getEntity());
        FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
        transaction.replace(R.id.question_info, QuestionInfoFragment.newInstance(question, callback.editable()));
        transaction.commit();
    }
}
