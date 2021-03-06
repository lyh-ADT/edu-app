package com.edu_app.controller.student.practice;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.edu_app.R;
import com.edu_app.model.Question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 做题时，展示所有的题目适配器
 */
public class DoExamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private List<Question> questions;
    private final static int TYPE_CHOICE = 0;
    private final static int TYPE_FILL = 1;

    private final static int TYPE_SHORTANSWER = 2;
    private Map<String,String> answer;


    public DoExamAdapter(Context context, JSONArray data) {
        this.answer = new HashMap<String, String>();
        this.context = context;
        this.questions = data.toJavaList(Question.class);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==TYPE_CHOICE){

            return new DoChoiceViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_stu_practice_doexam_choice,null));

        }else if(viewType==TYPE_FILL){
            return new DoFillViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_stu_practice_doexam_fill,null));

        }else {
            return new DoShortAnswerViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_stu_practice_doexam_shortanswer,null));

        }
    }
//绑定数据并将输入的数据放到answer中
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Question question = questions.get(position);
        if(holder instanceof DoFillViewHolder){
            ((DoFillViewHolder) holder).getExamtext().setText(question.getOrderNumber()+":"+question.getQuestion());
            ((DoFillViewHolder) holder).getAnswerEdit().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String rs = s.toString();
                    answer.remove(position+1);
                    answer.put(String.valueOf(position+1),rs);
                }
            });
        }else if(holder instanceof DoChoiceViewHolder){
            ((DoChoiceViewHolder) holder).getExamtext().setText(question.getOrderNumber()+":"+question.getQuestion());
            ((DoChoiceViewHolder) holder).getRadiogroup().setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    String radio =  ((RadioButton)group.findViewById(checkedId)).getText().toString();
                    answer.remove(position+1);
                    answer.put(String.valueOf(position+1),radio);
                }
            });
        }else if(holder instanceof DoShortAnswerViewHolder){
            ((DoShortAnswerViewHolder) holder).getExamtext().setText(question.getOrderNumber()+":"+question.getQuestion());
            ((DoShortAnswerViewHolder) holder).getAnswerEdit().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String rs = s.toString();
                    answer.remove(position+1);
                    answer.put(String.valueOf(position+1),rs);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return questions.size();
    }
    @Override
    public int getItemViewType(int position) {
        String type = questions.get(position).getQuestionType();
        if (type.equals(Question.QUESTION_TYPE_CHOICE)){
            return TYPE_CHOICE;

        }else if(type.equals(Question.QUESTION_TYPE_FILL)){
            return TYPE_FILL;

        }else {
            return TYPE_SHORTANSWER;
        }
    }
//    获取所有的结果,题号+答案
    public Map<String,String> getAnswer(){
        return answer;
    }
}

