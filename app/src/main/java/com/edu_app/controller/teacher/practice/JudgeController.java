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
import com.edu_app.model.NetworkUtility;
import com.edu_app.model.Question;
import com.edu_app.model.teacher.TeacherInfo;
import com.edu_app.model.teacher.practice.Judge;
import com.edu_app.model.teacher.practice.PracticeItem;
import com.edu_app.model.teacher.practice.QuestionItem;
import com.edu_app.model.teacher.practice.StudentPracticeInfo;
import com.edu_app.view.teacher.Fragment;
import com.edu_app.view.teacher.QuestionInfoFragment;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.Map;

public class JudgeController extends Controller {
    private StudentPracticeInfo stuInfo;
    private int questionIndex = 0;
    private Fragment fragment;
    private Judge model;
    private Callback callback;
    private PracticeItem practice;
    private int[] scores;
    private StudentPracticeAnswer answers = new StudentPracticeAnswer();

    class StudentPracticeAnswer{
        private Map<String, String> answers;

        public StudentPracticeAnswer(){

        }

        private void getAnswers(){
            new Thread(){
                @Override
                public void run(){
                    try {
                        answers = NetworkUtility.getToJson(model.getTeacherInfo().getHost()+"/judge_student?stuId="+stuInfo.getStuId()+"&practiceId="+stuInfo.getPracticeId(), model.getTeacherInfo().getUID(),  new TypeToken<Map<String, String>>(){}.getType());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        public int size(){
            return answers.size();
        }

        public String getAnswer(int i){
            while(answers == null){

            }
            return answers.get(String.valueOf(i));
        }
    }

    public interface Callback extends Controller.Callback{
        StudentPracticeInfo getPracticeInfo();
        PracticeItem getPractice();
    }
    @Override
    public void bindCallback(Controller.Callback callback){
        this.callback = (Callback)callback;
        stuInfo = this.callback.getPracticeInfo();
        this.practice = this.callback.getPractice();
        answers.getAnswers();
        scores = new int[practice.getQuestionCount()];
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
                String s = ((TextView)view.findViewById(R.id.score)).getText().toString();
                if(questionIndex < answers.size()-1){
                    if(setScore(s)) {
                        questionIndex++;
                        changeToQuestion(questionIndex);
                    }
                }else{
                    if(!setScore(s)) {
                        return;
                    }
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
                                                String response = model.sendJudgement(stuInfo.getPracticeId(), stuInfo.getStuId(), scores);
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
        Question question = practice.getQuestionAt(index).getEntity();
        question.setAnswer(answers.getAnswer(index+1));
        TextView question_tv = view.findViewById(R.id.question);
        question_tv.setText(question.getQuestion());

        FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
        transaction.replace(R.id.question_info, QuestionInfoFragment.newInstance(new QuestionItem(question), false));
        transaction.addToBackStack(null);
        transaction.commit();

        if(question.getScore() > 0){
            EditText score_et = view.findViewById(R.id.score);
            score_et.setText(String.valueOf(question.getScore()));
        }
    }
}
