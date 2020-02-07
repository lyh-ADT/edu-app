package com.edu_app.controller.teacher.practice;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.teacher.practice.PictureQuestionItem;
import com.edu_app.model.teacher.practice.QuestionItem;

public class AddPracticeItemController extends Controller {
    private static View.OnClickListener clickItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: 进入问题详情
        }
    };

    public AddPracticeItemController(View view, QuestionItem model){
        super(view, model);
        this.view = view;
        bindListener();
    }

    @Override
    protected void bindListener(){
        view.setOnClickListener(clickItemListener);
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
