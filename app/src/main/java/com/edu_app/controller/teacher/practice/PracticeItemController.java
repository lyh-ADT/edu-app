package com.edu_app.controller.teacher.practice;

import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.edu_app.R;
import com.edu_app.model.teacher.practice.PracticePage;
import com.edu_app.model.teacher.practice.PracticeItem;

public class PracticeItemController {
    static private boolean deleteMode = false;
    private PracticePage pageModel;
    private View.OnClickListener clickItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: 进入练习详情
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
            }
        }
    };
    private final PracticeItem model;
    private View view;

    public PracticeItemController(View view, PracticeItem model, PracticePage pageModel){
        this.view = view;
        this.model = model;
        this.pageModel = pageModel;
        setValues();
        bindListener();
    }

    static public void setDeleteMode(boolean active){
        deleteMode = active;
    }

    private void setValues(){
        TextView title = view.findViewById(R.id.practice_item_title);
        title.setText(model.getTitle());
    }

    private void bindListener(){
        view.setOnClickListener(clickItemListener);
    }
}
