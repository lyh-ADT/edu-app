package com.edu_app.model.student;

import com.edu_app.model.Model;

public class ChatMsg extends Model {
    private String userId;
    private String content;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
