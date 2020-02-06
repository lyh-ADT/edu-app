package com.edu_app.model;

import java.io.Serializable;
import java.util.Objects;

public class Question implements Serializable {
    private int orderNumber;
    private String questionType;
    private String question;
    private String answer;

    public Question() {}

    public Question(int orderNumber, String questionType, String question) {
        this.orderNumber = orderNumber;
        this.questionType = questionType;
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return questionType.equals(question1.questionType) &&
                question.equals(question1.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionType, question);
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestion() {
        return question == null? "":question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
