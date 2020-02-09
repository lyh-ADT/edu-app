package com.edu_app.controller.teacher.practice;

import android.content.DialogInterface;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.Question;
import com.edu_app.model.teacher.TeacherInfo;
import com.edu_app.model.teacher.practice.Judge;
import com.edu_app.model.teacher.practice.StudentPracticeItem;
import com.edu_app.model.teacher.question.QuestionItemFactory;
import com.edu_app.view.teacher.Fragment;
import com.edu_app.view.teacher.QuestionInfoFragment;

import java.io.IOException;

public class JudgeController extends Controller {
    private StudentPracticeItem practice;
    private int questionIndex = 0;
    private Fragment fragment;
    private Judge model;
    private Callback callback;
    private int[] scores;

    public interface Callback extends Controller.Callback{
        StudentPracticeItem getPractice();
    }
    @Override
    public void bindCallback(Controller.Callback callback){
        this.callback = (Callback)callback;
        practice = this.callback.getPractice();
        scores = new int[practice.getPractice().getQuestions().size()];
        changeToQuestion(questionIndex);
    }

    public JudgeController(View view, Fragment fragment, TeacherInfo teacherInfo) {
        super(view, new Judge(teacherInfo));
        this.fragment = fragment;
        this.model = (Judge)super.model;
        bindListener();
    }

    @Override
    protected void bindListener(){
        Button lastQuestion_btn = view.findViewById(R.id.last);
        lastQuestion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionIndex > 0){
                    String s = ((TextView)view.findViewById(R.id.score)).getText().toString();
                    if(setScore(s)){
                        questionIndex--;
                        changeToQuestion(questionIndex);
                    }
                }
            }
        });

        Button nextQuestion_btn = view.findViewById(R.id.next);
        nextQuestion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionIndex < practice.getPractice().getQuestions().size()-1){
                    String s = ((TextView)view.findViewById(R.id.score)).getText().toString();
                    if(setScore(s)) {
                        questionIndex++;
                        changeToQuestion(questionIndex);
                    }
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("确认提交批改？")
                            .setTitle("确认提交")
                            .setCancelable(true)
                            .setPositiveButton("取消", null)
                            .setNegativeButton("提交", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final android.app.AlertDialog loadingDialog = showProgressBar(view.getContext(), "提交中...");
                                    new Thread(){
                                        @Override
                                        public void run(){
                                            try {
                                                String response = model.sendJudgement(practice.getPractice().getId(), practice.getAuthorId(), scores);
                                                if(!"success".equals(response)){
                                                    Looper.prepare();
                                                    loadingDialog.dismiss();
                                                    Toast.makeText(view.getContext(), response, Toast.LENGTH_LONG).show();
                                                    Looper.loop();
                                                } else {
                                                    loadingDialog.dismiss();
                                                    fragment.getFragmentManager().popBackStack();
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }.start();

                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    private boolean setScore(String s){
        if(s.length() <= 0){
            Toast.makeText(view.getContext(), "请打分", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            int score;
            try{
                score = Integer.parseInt(s);
            }catch (NumberFormatException e){
                Toast.makeText(view.getContext(), "请输入数字", Toast.LENGTH_SHORT).show();
                return false;
            }
            scores[questionIndex] = score;
        }
        return true;
    }

    private void changeToQuestion(int index){
        Question question = practice.getPractice().getQuestions().get(index);
        TextView question_tv = view.findViewById(R.id.question);
        question_tv.setText(question.getQuestion());

        FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
        transaction.replace(R.id.question_info, QuestionInfoFragment.newInstance(QuestionItemFactory.newInstance(question.getQuestionType(), question), false));
        transaction.commit();

        if(question.getScore() > 0){
            EditText score_et = view.findViewById(R.id.score);
            score_et.setText(String.valueOf(question.getScore()));
        }
    }
}
