package com.edu_app.controller.teacher.practice;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.teacher.practice.PracticePage;
import com.edu_app.model.teacher.practice.PracticeItem;
import com.edu_app.view.teacher.Fragment;

public class PracticeItemController extends Controller {
    static private boolean deleteMode = false;
    private PracticePage pageModel;
    private View.OnClickListener clickItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(deleteMode){
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("删除"+model.getTitle())
                        .setTitle("确认删除")
                        .setCancelable(true)
                        .setPositiveButton("取消", null)
                        .setNegativeButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pageModel.deletePractice(model);
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                FragmentManager manager = fragment.getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.main_fragment, Fragment.newInstance("practice_info", null, new PracticeInfoController.Callback() {
                    @Override
                    public PracticeItem getPractice() {
                        return model;
                    }

                    @Override
                    public void addPractice(PracticeItem practiceItem) {}

                    @Override
                    public boolean editable() {
                        return false;
                    }

                    @Override
                    public void show() {
                        FragmentManager manager = fragment.getFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.show(fragment);
                        transaction.commit();
                    }
                }));
                transaction.hide(fragment);
                transaction.commit();
            }
        }
    };
    private final PracticeItem model;
    private View view;
    private android.app.Fragment fragment;

    public PracticeItemController(View view, PracticeItem model, PracticePage pageModel, android.app.Fragment fragment){
        super(view, model);
        this.view = view;
        this.model = model;
        this.pageModel = pageModel;
        this.fragment = fragment;
        setValues();
        bindListener();
    }

    static public void setDeleteMode(boolean active){
        deleteMode = active;
    }

    @Override
    protected void bindListener(){
        view.setOnClickListener(clickItemListener);
    }
}
