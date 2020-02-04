package com.edu_app.controller.teacher;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.util.Pair;

import com.edu_app.model.teacher.Model;

import java.util.List;

public class Controller {
    protected View view;
    protected Model model;

    public Controller(View view, Model model){
        this.view = view;
        this.model = model;
        setValues();
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

    }
    protected void bindListener(){

    }

    protected void setValues() {
        List<Pair<Integer, Object>> showingFields = model.getShowField();
        if(showingFields == null){
            return;
        }
        for (Pair<Integer, Object> pair : showingFields) {
            if (pair.first == null || pair.second == null) {
                Log.e("Controller", "setValues model子类的getShowField方法返回值不能有null");
                return;
            }

            setValue(view.findViewById(pair.first), pair.second);
        }
    }

    private void setValue(View v, Object value){
        boolean success = false;
        if(v instanceof TextView){
            success = setTextView((TextView) v, value);
        }
        if(!success){
            Log.e("Controller", "未定义setValue: ("+v.getClass()+", "+value.getClass()+")");
        }
    }

    private boolean setTextView(TextView view, Object value){
        if(value instanceof String){
            view.setText((String)value);
            return true;
        }
        return false;
    }
}
