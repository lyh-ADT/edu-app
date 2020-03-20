package com.edu_app.vediochat.controller;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.edu_app.R;

import com.edu_app.model.student.ChatMsg;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context context;
    private List<ChatMsg> msgList;

    public ChatAdapter(Context context, List<ChatMsg> msgList){
        this.context = context;
        this.msgList =msgList;

        Log.d("webRtcHelper",msgList.get(0).getContent());

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
        Log.d("webRtcHelper",msgList.get(0).getContent()+"================");

        holder.userMsg.setText(msgList.get(position).getUserId()+": "+msgList.get(position).getContent());

    }


    @Override
    public int getItemCount() {

            return msgList.size();


    }
}
