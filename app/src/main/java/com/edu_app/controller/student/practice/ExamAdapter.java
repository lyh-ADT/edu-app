package com.edu_app.controller.student.practice;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu_app.R;
import com.edu_app.model.Practice;

import java.util.ArrayList;

/**
 * 展示所有的练习题的适配器
 */
public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ViewHolder> {
    private final Context context;
    private final Integer DONE = 1;
    private final Integer NODONE = 0;
    private ArrayList<Practice> practicelist;
    //    定义点击事件接口
    private OnItemClickListener itemlistener;

    public ExamAdapter(Context context, ArrayList<Practice> practicelist) {
        this.context = context;
        this.practicelist = practicelist;
    }

    //    此处可以通过外部去设置监听器
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemlistener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView examtitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            examtitle = itemView.findViewById(R.id.practicePage_practice_examtitle);
            itemView.setOnClickListener(this);
        }

        // 每个Item回调接口，传递数据
        @Override
        public void onClick(View v) {

            itemlistener.OnItemClick(v, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public ExamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.activity_stu_practice_subjectrecycler, null);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamAdapter.ViewHolder holder, int position) {
        Practice practice = practicelist.get(position);
        holder.examtitle.setText(practice.getTitle());
        if (holder.getItemViewType() == DONE) {
            holder.examtitle.setTextColor(Color.BLUE);
        }else{
            holder.examtitle.setTextColor(Color.RED);

        }
    }

    @Override
    public int getItemCount() {
        return practicelist.size();
    }

    public int getItemViewType(int position) {
        Boolean type = practicelist.get(position).getDone();
        if (type.equals(true)) {
            return DONE;
        } else {
            return NODONE;
        }
    }

}
