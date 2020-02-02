package com.edu_app.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Practice implements Serializable {
    private String id;
    private List<Question> questions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Practice practice = (Practice) o;
        return id.equals(practice.id) &&
                questions.equals(practice.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, questions);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
