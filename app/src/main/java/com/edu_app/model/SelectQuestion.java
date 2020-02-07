package com.edu_app.model;

import java.util.ArrayList;
import java.util.List;

public class SelectQuestion extends Question {
    private List<String> selections = new ArrayList<>();

    public SelectQuestion(){
        super(0, "select", "");
    }

    public boolean addSelection(String answer){
        selections.add(answer);
        return true;
    }

    public int getSelectionCount(){
        return selections.size();
    }

    public String getSelectionAt(int i){
        return selections.get(i);
    }

    public int removeSelectionAt(String order){
        int index = 0;
        int power = 1;
        for(int i=order.length()-1; i >= 0; --i){
            index += (order.charAt(i) - 'A'+1) * power;
            power *= 26;
        }
        selections.remove(index-1);
        return index-1;
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
}
