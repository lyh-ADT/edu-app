package com.edu_app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Practice implements Serializable {
    private String id;
    private String title;
    private List<Question> questions = new ArrayList<>();

    public Practice() {}

    public Practice(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Practice practice = (Practice) o;
        return id.equals(practice.id) &&
                questions.equals(practice.questions) &&
                title.equals(practice.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, questions, title);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
