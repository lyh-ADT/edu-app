package com.edu_app.controller.teacher.question;

import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.teacher.practice.QuestionItem;
import com.edu_app.view.teacher.Fragment;
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
        setFullScreen(fragment.getActivity());
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
        question_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String q = s.toString();
                if(q.length() <= 0){
                    question.setQuestion(null);
                }else{
                    question.setQuestion(q);
                }
            }
        });


        final EditText score_et = view.findViewById(R.id.score);
        score_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String q = s.toString();
                if(q.length() <= 0){
                    question.setScore(0);
                }else{
                    try{
                        question.setScore(Integer.parseInt(q));
                    }catch (NumberFormatException e){
                        Toast.makeText(fragment.getContext(), "请输入一个正整数", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        Button addQuestion_btn = view.findViewById(R.id.add_question_btn);
        addQuestion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("practiceItem",question.getQuestion()+question.getQuestionType()+question.getScore()+question.getAnswer());
                if(question.getQuestion() == null || question.getAnswer() == null || question.getScore() == 0){
                    Toast.makeText(view.getContext(), "信息不完整", Toast.LENGTH_LONG).show();
                    return;
                }
                callback.addQuestion(question);
                FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
                transaction.remove(fragment);
                transaction.commit();
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
        final EditText score_et = view.findViewById(R.id.score);

        if(editable){
            question_type.setEnabled(true);
            question_et.setEnabled(true);
            addQuestion_btn.setVisibility(View.VISIBLE);
            score_et.setEnabled(true);
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
                callback.show();
            }
        });

        score_et.setText(String.valueOf(question.getScore()));
        score_et.setEnabled(false);
    }

    private void changeToQuestionType() {
        question = new QuestionItem();
        question.setQuestionType(questionType);
        // 补上设置问题，避免切换题目类型导致问题没有设置到对应的QuestionItem
        String q = ((TextView)view.findViewById(R.id.input_question_edit)).getText().toString();
        if(q.length() > 0){
            question.setQuestion(q);
        }
        if(callback.editable()){
            ((EditText)view.findViewById(R.id.score)).setText(null);
        }
        FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
        transaction.replace(R.id.question_info, QuestionInfoFragment.newInstance(question, callback.editable()));
        transaction.commit();
    }
}
