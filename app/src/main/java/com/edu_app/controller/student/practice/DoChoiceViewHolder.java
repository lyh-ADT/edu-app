package com.edu_app.controller.student.practice;

import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu_app.R;

public class DoChoiceViewHolder extends RecyclerView.ViewHolder {
    public TextView examtext;
    public RadioGroup radiogroup;
    public DoChoiceViewHolder(@NonNull View itemView) {
        super(itemView);
        examtext=itemView.findViewById(R.id.practicePage_practice_doExam_choiceView);
        radiogroup=itemView.findViewById(R.id.practicePage_practice_doExam_choiceRadioGroup);
    }
}
