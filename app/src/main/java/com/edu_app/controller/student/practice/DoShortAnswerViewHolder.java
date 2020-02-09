package com.edu_app.controller.student.practice;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu_app.R;

public class DoShortAnswerViewHolder extends RecyclerView.ViewHolder {
    private TextView examtext;
    private EditText answeredit;
    public DoShortAnswerViewHolder(@NonNull View itemView) {
        super(itemView);
        examtext=itemView.findViewById(R.id.practicePage_practice_doExam_shortAnswerView);
        answeredit =itemView.findViewById(R.id.practicePage_practice_doExam_shortAnswerEditText);
    }
    public TextView getExamtext() {
        return examtext;
    }

    public EditText getAnswerEdit() {
        return answeredit;
    }
}
