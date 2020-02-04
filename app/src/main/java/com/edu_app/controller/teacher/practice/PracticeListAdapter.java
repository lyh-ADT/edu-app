package com.edu_app.controller.teacher.practice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.edu_app.R;
import com.edu_app.model.teacher.practice.PracticePage;

public class PracticeListAdapter extends BaseAdapter {
    private final PracticePage model;
    private LayoutInflater inflater;

    public PracticeListAdapter(LayoutInflater inflater, PracticePage model){
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
        if(position >= -1){
            convertView = inflater.inflate(R.layout.fragment_teacher_practice_item, parent, false);
            new PracticeItemController(convertView, model.getPracticeItemAt(position), model);
        }
        return convertView;
    }
}
