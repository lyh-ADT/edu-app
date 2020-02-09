package com.edu_app.controller.teacher.practice;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.Question;
import com.edu_app.model.teacher.TeacherInfo;
import com.edu_app.model.teacher.practice.Judge;
import com.edu_app.model.teacher.practice.QuestionItem;
import com.edu_app.model.teacher.practice.StudentPracticeItem;
import com.edu_app.model.teacher.question.QuestionItemFactory;
import com.edu_app.view.teacher.Fragment;
import com.edu_app.view.teacher.QuestionInfoFragment;

public class JudgeController extends Controller {
    private StudentPracticeItem practice;
    private int questionIndex = 0;
    private Fragment fragment;
    private Judge model;
    private Callback callback;

    public interface Callback extends Controller.Callback{
        StudentPracticeItem getPractice();
    }
    @Override
    public void bindCallback(Controller.Callback callback){
        this.callback = (Callback)callback;
        practice = this.callback.getPractice();
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
