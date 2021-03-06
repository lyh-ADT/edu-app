package com.edu_app.model.teacher.stream;

import androidx.core.util.Pair;

import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.NetworkUtility;
import com.edu_app.model.teacher.Model;
import com.edu_app.model.teacher.TeacherInfo;

import java.io.IOException;
import java.util.List;

public class LiveStreamPage implements Model {
    private TeacherInfo info;

    public LiveStreamPage(TeacherInfo info){
        this.info = info;
    }

    @Override
    public List<Pair<Integer, Object>> getShowField() {
        return null;
    }

    @Override
    public void setController(Controller controller) {

    }

    public void sendChatMessage(final String message){
        new Thread(){
            @Override
            public void run(){
                try {
                    NetworkUtility.postRequest(info.getHost()+"/stream_chat", info.getUID(), message.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public String getChatRoomUrl(){
        return info.getHost()+"/chat_room";
    }
}
