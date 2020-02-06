package com.edu_app.controller.student.practice;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.edu_app.R;
import com.edu_app.model.Practice;
import com.edu_app.view.student.pracitce.activitySubjectMath;

import java.util.ArrayList;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<Practice> practicelist;
//    定义点击事件接口
    private OnItemClickListener itemlistener;
    public ExamAdapter(Context context, ArrayList<Practice> practicelist) {
        this.context = context;
        this.practicelist = practicelist;
    }
    //    此处可以通过外部去设置监听器
    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemlistener = listener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView examtitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            examtitle = (TextView) itemView.findViewById(R.id.practicePage_practice_examtitle);
            itemView.setOnClickListener(this);
        }
//      每个Item回调接口，传递数据
        @Override
        public void onClick(View v) {

            itemlistener.OnItemClick(v,getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public ExamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.activity_stu_practice_subjectrecycler,null);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamAdapter.ViewHolder holder, int position) {
        Practice practice = practicelist.get(position);
        holder.examtitle.setText(practice.getTitle());
    }

    @Override
    public int getItemCount() {
        return practicelist.size();
    }

}
