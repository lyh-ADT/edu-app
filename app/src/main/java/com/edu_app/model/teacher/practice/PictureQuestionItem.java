package com.edu_app.model.teacher.practice;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class PictureQuestionItem extends QuestionItem {
    private List<Uri> imageUris = new ArrayList<>();

    public void addUri(Uri uri){
        imageUris.add(uri);
    }

    public List<Uri> getImageUris() {
        return imageUris;
    }

    public void setImageUris(List<Uri> imageUris) {
        this.imageUris = imageUris;
    }
}
