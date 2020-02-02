package com.edu_app.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.edu_app.R;
import com.edu_app.model.TeacherAddPractice;

public class TeacherAddPracticeListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private TeacherAddPractice model;

    public TeacherAddPracticeListAdapter(LayoutInflater inflater, TeacherAddPractice model){
        this.inflater = inflater;
        this.model = model;
    }

    @Override
    public int getCount() {
        return model.getQuestionCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(position > -1){
            convertView = inflater.inflate(R.layout.item_question, parent, false);
            new TeacherAddPracticeItemController(convertView, model.getQuestionAt(position));
        }
        return convertView;
    }
}
