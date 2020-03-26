package com.edu_app.model.student;

import androidx.annotation.NonNull;

import com.edu_app.model.Model;


public class ChatData extends Model {
    private String type;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
