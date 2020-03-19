package com.edu_app.vediochat.controller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu_app.R;

import com.edu_app.model.student.ChatMsg;


import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context context;
    private List msgList;

    public ChatAdapter(Context context, List msgList){
        this.context = context;
        this.msgList =msgList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userMsg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userMsg = itemView.findViewById(R.id.stu_course_chatSingle_chatMsg);


        }
    }
    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.activity_stu_course_chatadapter, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(msgList.get(position).toString());
        ChatMsg msg= com.alibaba.fastjson.JSONObject.parseObject(jsonObject.getString("data"),ChatMsg.class
        );
        holder.userMsg.setText(msg.getUserId()+": "+msg.getContent());

    }


    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
