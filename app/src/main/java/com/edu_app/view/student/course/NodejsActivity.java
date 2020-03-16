package com.edu_app.view.student.course;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.edu_app.R;
import com.edu_app.controller.student.course.WebrtcUtil;

/**
 *
 * 聊天活动页面，服务器使用nodejs
 */
public class NodejsActivity extends AppCompatActivity {
    private EditText et_room;
    private final String serverUrl = "wss://139.159.176.78:3000/teacher-stream/wss";
    private String uuid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodejs);
        initView();
        initVar();

    }

    private void initView() {
        et_room = findViewById(R.id.et_room);
    }

    private void initVar() {
        et_room.setText("");
    }

    /*-------------------------- nodejs版本服务器测试--------------------------------------------*/
    public void JoinRoomSingleVideo(View view) {
        Intent intent = this.getIntent();
        uuid = intent.getStringExtra("uuid");
        WebrtcUtil.callSingle(this,
                serverUrl,
                et_room.getText().toString().trim(),
                true,uuid);
    }

    public void JoinRoomSingleAudio(View view) {
        WebrtcUtil.callSingle(this,
                serverUrl,
                et_room.getText().toString().trim(),
                false,uuid);
    }

    public void JoinRoom(View view) {
        WebrtcUtil.call(this, serverUrl, et_room.getText().toString().trim(),uuid);

    }


}
