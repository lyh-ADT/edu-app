package com.edu_app.model.teacher.practice;

import androidx.core.util.Pair;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.Question;
import com.edu_app.model.teacher.Model;
import com.edu_app.model.Practice;
import com.edu_app.model.teacher.addquestion.QuestionItemFactory;

import java.util.ArrayList;
import java.util.List;

public class PracticeItem implements Model {
    private Practice practice;

    public PracticeItem(Practice practice){
        if(practice == null){
            practice = new Practice();
        }
        this.practice = practice;
    }

    @Override
    public List<Pair<Integer, Object>> getShowField() {
        ArrayList<Pair<Integer, Object>> list = new ArrayList<>();
        list.add(new Pair<Integer, Object>(R.id.practice_item_title, practice.getTitle()));
        return list;
    }

    @Override
    public void setController(Controller controller) {

    }

    public int getQuestionCount(){
        return practice.getQuestions().size();
    }

    public void addQuestion(QuestionItem questionItem){
        practice.getQuestions().add(questionItem.getEntity());
    }

    public QuestionItem getQuestionAt(int pos){
        Question question = practice.getQuestions().get(pos);
        return QuestionItemFactory.newInstance(question.getQuestionType(), question);
    }

    public void deleteQuestion(QuestionItem questionItem){
        practice.getQuestions().remove(questionItem.getEntity());
    }

    public String getId(){
        return practice.getId();
    }

    public String getTitle(){
        return practice.getTitle();
    }

    public Practice getEntity(){
        return practice;
    }
}
