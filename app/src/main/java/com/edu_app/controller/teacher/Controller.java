package com.edu_app.controller.teacher;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;

import com.edu_app.R;
import com.edu_app.model.teacher.Model;

import java.io.Serializable;
import java.util.List;

public class Controller {
    protected View view;
    protected Model model;

    public Controller(View view, Model model){
        this.view = view;
        this.model = model;
        setValues();
    }

    public int dip2px(float dpValue) {
        final float scale = view.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {}

    public void onConfigurationChanged(Configuration config){}

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {}

    public interface Callback extends Serializable {}

    public void bindCallback(Callback callback){}

    public void setFullScreen(Activity activity){
        // 关闭标题栏
        ActionBar actionBar = activity.getActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        // 关闭底部的导航栏
        View navigation_bar = activity.findViewById(R.id.navigation_bar);
        navigation_bar.setVisibility(View.GONE);
    }

    public void unSetFullScreen(Activity activity){
        // 关闭标题栏
        ActionBar actionBar = activity.getActionBar();
        if(actionBar != null){
            actionBar.show();
        }
        // 关闭底部的导航栏
        View navigation_bar = activity.findViewById(R.id.navigation_bar);
        navigation_bar.setVisibility(View.VISIBLE);
    }

    protected void bindListener(){}

    protected void setValues() {
        if(model == null){
            return;
        }
        List<Pair<Integer, Object>> showingFields = model.getShowField();
        if(showingFields == null){
            return;
        }
        for (Pair<Integer, Object> pair : showingFields) {
            if (pair.first == null || pair.second == null) {
                Log.e("Controller", "setValues model子类的getShowField方法返回值不能有null:"+this.getClass());
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
            if(v == null){
                Log.e("Controller", "视图上没找到对应的组件");
            }else{
                Log.e("Controller", "未定义setValue: ("+v.getClass()+", "+value.getClass()+")");
            }
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
