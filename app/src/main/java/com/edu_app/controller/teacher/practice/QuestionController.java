package com.edu_app.controller.teacher.practice;

import androidx.fragment.app.FragmentTransaction;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.controller.teacher.question.QuestionInfoController;
import com.edu_app.model.teacher.practice.PictureQuestionItem;
import com.edu_app.model.teacher.practice.QuestionItem;
import com.edu_app.view.teacher.Fragment;

public class QuestionController extends Controller {
    private static boolean deleteMode = false;
    private QuestionItem model;
    private Fragment fragment;
    private Callback callback;

    public QuestionController(View view, QuestionItem model, Fragment fragment){
        super(view, model);
        this.view = view;
        this.model = model;
        this.fragment = fragment;
        bindListener();
    }

    public static void setDeleteMode(boolean deleteMode){
        QuestionController.deleteMode = deleteMode;
    }

    public interface Callback extends Controller.Callback{
        void addQuestion(QuestionItem questionItem);
        void deleteQuestion(QuestionItem questionItem);
    }

    @Override
    public void bindCallback(Controller.Callback callback){
        this.callback = (Callback) callback;
    }

    @Override
    protected void bindListener(){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deleteMode){
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("删除第"+model.getOrderNumber()+"题")
                            .setTitle("确认删除")
                            .setCancelable(true)
                            .setPositiveButton("取消", null)
                            .setNegativeButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    callback.deleteQuestion(model);
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }
                FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
                com.edu_app.view.teacher.Fragment questionFragment = com.edu_app.view.teacher.Fragment.newInstance("question_info", null, new QuestionInfoController.Callback() {
                    @Override
                    public void addQuestion(QuestionItem questionItem) {
                        callback.addQuestion(questionItem);
                    }

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
