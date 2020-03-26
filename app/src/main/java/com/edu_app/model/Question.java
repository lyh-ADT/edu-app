package com.edu_app.model;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public class Question extends Model implements Serializable {
    public static final HashMap<String, String> typeText;
    public   static final String QUESTION_TYPE_CHOICE = "CHOICE";
    public   static final String QUESTION_TYPE_FILL = "FILL";
    public   static final String QUESTION_TYPE_SHORT_ANSWER = "SHORT_ANSWER";
    static {
        typeText = new HashMap<String, String>();
        typeText.put(QUESTION_TYPE_CHOICE, "选择");
        typeText.put(QUESTION_TYPE_FILL, "填空");
        typeText.put(QUESTION_TYPE_SHORT_ANSWER, "简答");
    }
    private int orderNumber;
    private String questionType;
    private String question;
    private String answer;
    private int score;

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
