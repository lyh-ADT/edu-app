package com.edu_app.controller.student.practice;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu_app.R;

public class DoShortAnswerViewHolder extends RecyclerView.ViewHolder {
    public TextView examtext;
    public EditText answer;
    public DoShortAnswerViewHolder(@NonNull View itemView) {
        super(itemView);
        examtext=itemView.findViewById(R.id.practicePage_practice_doExam_shortAnswerView);
        answer =itemView.findViewById(R.id.practicePage_practice_doExam_shortAnswerEditText);
    }
}
