package com.edu_app.model.teacher.addquestion;

import com.edu_app.model.SelectQuestion;
import com.edu_app.model.teacher.practice.QuestionItem;

public class SelectQuestionItem extends QuestionItem {
    private SelectQuestion question;

    public SelectQuestionItem(SelectQuestion question){
        super(question);
        if(question == null){
            question = new SelectQuestion();
        }
        super.question = question;
        this.question = question;
    }

    public String nextOrderString(String s){
        char[] array = s.toCharArray();
        boolean finish = false;
        for(int i=array.length-1; i >= 0; --i){
            if(array[i] < 'Z'){
                array[i] += 1;
                finish = true;
                break;
            } else {
                array[i] = 'A';
            }
        }
        if(!finish){
            return "A"+String.valueOf(array);
        }
        return String.valueOf(array);
    }

    public void addSelection(String question_string) {
        question.addSelection(question_string);
    }

    public int removeSelectionAt(String order) {
        int index = 0;
        int power = 1;
        for(int i=order.length()-1; i >= 0; --i){
            index += (order.charAt(i) - 'A'+1) * power;
            power *= 26;
        }
        question.remove(index-1);
        return index-1;
    }

    public int getSelectionCount() {
        return question.getSelectionCount();
    }

    public String getSelectionAt(int position) {
        return question.getSelectionAt(position);
    }
}
