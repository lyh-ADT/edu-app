package com.edu_app.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.edu_app.R;
import com.edu_app.model.TeacherPractice;

public class TeacherPracticeListAdapter extends BaseAdapter {
    private final TeacherPractice model;
    private LayoutInflater inflater;

    public TeacherPracticeListAdapter(LayoutInflater inflater, TeacherPractice model){
        this.inflater = inflater;
        this.model = model;
    }

    @Override
    public int getCount() {
        return model.getPracticeItemCount();
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
        if(convertView == null){
            convertView = inflater.inflate(R.layout.fragment_teacher_practice_item, parent, false);
            new TeacherPracticeItemController(convertView, model.getPracticeItemAt(position));
        }
        return convertView;
    }
}
