package com.edu_app.vediochat.controller;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu_app.R;

import com.edu_app.model.student.ChatMsg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context context;
    private JSONArray msgList;

    public ChatAdapter(Context context, JSONArray msgList){
        this.context = context;
        this.msgList =msgList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userId;
        private TextView userMsg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userMsg = itemView.findViewById(R.id.stu_course_chatSingle_userMsg);
            userId = itemView.findViewById(R.id.stu_course_chatSingle_userID);

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
        ChatMsg msg = null;
        try {

            com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(msgList.get(position).toString());
            msg= com.alibaba.fastjson.JSONObject.parseObject(jsonObject.getString("data"),ChatMsg.class
            );

            holder.userId.setText(msg.getUserId());
            holder.userMsg.setText(msg.getContent());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return msgList.length();
    }
}
