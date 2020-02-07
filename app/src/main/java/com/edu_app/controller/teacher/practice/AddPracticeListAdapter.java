package com.edu_app.controller.teacher.practice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.edu_app.R;
import com.edu_app.model.teacher.practice.AddPractice;

public class AddPracticeListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private AddPractice model;

    public AddPracticeListAdapter(LayoutInflater inflater, AddPractice model){
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
            new AddPracticeItemController(convertView, model.getQuestionAt(position));
        }
        return convertView;
    }
}
