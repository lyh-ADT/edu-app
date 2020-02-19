package com.edu_app.controller.student.practice;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edu_app.R;

import java.util.Map;

/**
 *
 * 查看习题的适配器
 */
public class LookExamAdapter extends RecyclerView.Adapter<LookExamAdapter.ViewHolder> {

    private final JSONArray examDetail;
    private final Map scoreDetail;
    private final String fullScore;
    private final JSONArray teaAnswer;
    private final JSONObject stuAnswer;
    private final String stuScore;
    private Context context;
/*
     * 返回的数据包括试卷总体信息，以及详细题目，学生的答案，正确答案，学生每题得分
     * {practiceInfo:{练习id:"",本次总分:""...},everyQuestion:{题号:"1",问题:"question",学生答案:"stu_answer",学生分数:"",此题满分:""...}...}
     */
    private Map<String,Map<String, String>> questionAnswerScore;

    public LookExamAdapter(Context context, JSONObject data) {
        this.context = context;

        this.examDetail = JSONObject.parseArray(data.getString("examDetail"));
        this.scoreDetail = JSONObject.parseObject(data.getString("scoreDetail"));
        this.fullScore = data.getString("fullScore");
        this.teaAnswer = JSONObject.parseArray(data.getString("teaAnswer"));
        this.stuAnswer = JSONObject.parseObject(data.getString("stuAnswer"));
        this.stuScore = data.getString("stuScore");
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView questiontext;
        private TextView stu_answertext;
        private TextView right_answertext;
//        学生本题得分
        private TextView stuscore;
//        本题满分
        private TextView fullscore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questiontext = itemView.findViewById(R.id.practicePage_practice_lookexam_question);
            stu_answertext = itemView.findViewById(R.id.practicePage_practice_lookexam_stuanswer);
            right_answertext = itemView.findViewById(R.id.practicePage_practice_lookexam_rightanswer);
            stuscore = itemView.findViewById(R.id.practicePage_practice_lookexam_stuscore);
            fullscore = itemView.findViewById(R.id.practicePage_practice_lookexam_fullscore);

        }
    }

    @NonNull
    @Override
    public LookExamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.activity_stu_practice_lookexam_item, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LookExamAdapter.ViewHolder holder, int position) {

        holder.questiontext.setText(examDetail.getJSONObject(position).getString("question"));
        if(scoreDetail==null){
            holder.stuscore.setText("未批改");
            holder.stuscore.setTextColor(Color.RED);
        }else {
            holder.stuscore.setText(scoreDetail.get(position+1).toString());
        }

        holder.fullscore.setText(examDetail.getJSONObject(position).getString("score"));
        holder.stu_answertext.setText(stuAnswer.getString(String.valueOf(position+1)));
        holder.right_answertext.setText(teaAnswer.getJSONObject(position).getString("answer"));
    }

    @Override
    public int getItemCount() {
        return this.examDetail.size();
    }
}