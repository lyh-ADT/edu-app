package com.edu_app.controller.teacher.practice;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.controller.teacher.question.QuestionInfoController;
import com.edu_app.model.teacher.practice.PictureQuestionItem;
import com.edu_app.model.teacher.practice.QuestionItem;

public class QuestionController extends Controller {
    private QuestionItem model;
    private Fragment fragment;

    public QuestionController(View view, QuestionItem model, Fragment fragment){
        super(view, model);
        this.view = view;
        this.model = model;
        this.fragment = fragment;
        bindListener();
    }

    @Override
    protected void bindListener(){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
                com.edu_app.view.teacher.Fragment questionFragment = com.edu_app.view.teacher.Fragment.newInstance("question_info", null, new QuestionInfoController.Callback() {
                    @Override
                    public void addQuestion(QuestionItem questionItem) {}

                    @Override
                    public void show() {
                        FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
                        transaction.show(fragment);
                        transaction.commit();
                    }

                    @Override
                    public boolean editable(){
                        return false;
                    }

                    @Override
                    public QuestionItem getQuestion(){
                        return model;
                    }
                });
                transaction.hide(fragment);
                transaction.add(R.id.main_fragment, questionFragment);
                transaction.commit();
            }
        });
    }

    @Override
    protected void setValues(){
        super.setValues();
        if(model instanceof PictureQuestionItem){
            ViewGroup viewGroup = (ViewGroup)view;
            for(Uri uri : ((PictureQuestionItem) model).getImageUris()){
                ImageView iv = new ImageView(view.getContext());
                iv.setImageURI(uri);
                iv.setAdjustViewBounds(true);
                iv.setMaxHeight(100);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                viewGroup.addView(iv);
            }
        }
    }
}
