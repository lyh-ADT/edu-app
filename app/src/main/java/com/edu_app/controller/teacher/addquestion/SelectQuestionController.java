package com.edu_app.controller.teacher.addquestion;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.Question;
import com.edu_app.model.SelectQuestion;

import java.util.ArrayList;

public class SelectQuestionController extends Controller {
    private SelectQuestion model;
    private ArrayList<String> optionOrders = new ArrayList<>();

    public SelectQuestionController(View view, Question model) {
        super(view, null);
        this.model = (SelectQuestion) model;
        optionOrders.add("请选择");
        bindListener();
    }

    @Override
    protected void bindListener(){
        ListView option_list = view.findViewById(R.id.option_list_lv);
        final ListAdapter adapter = new ListAdapter();
        option_list.setAdapter(adapter);

        Button addOption = view.findViewById(R.id.add_option);
        addOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogView = View.inflate(view.getContext(), R.layout.dialog_add_question, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("添加选项")
                        .setCancelable(true)
                        .setView(dialogView)
                        .setPositiveButton("取消", null)
                        .setNegativeButton("添加", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TextView question_edit = dialogView.findViewById(R.id.input_question_edit);
                                String question_string = question_edit.getText().toString();
                                if(question_string.length() <= 0){
                                    Toast.makeText(view.getContext(), "选项不能为空", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                model.addSelection(question_string);
                                adapter.notifyDataSetChanged();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        final Spinner correct_answer = view.findViewById(R.id.correct_answer);
        ArrayAdapter<String> a = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, optionOrders);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        correct_answer.setAdapter(a);
    }

    private class ListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return model.getSelectionCount();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(position > -1){
                convertView = View.inflate(view.getContext(), R.layout.item_teacher_select_question, null);
                String order = "A";
                if(optionOrders.size() == 1 ){
                    optionOrders.add(order);
                } else if(position < optionOrders.size()){
                    order = optionOrders.get(position);
                } else {
                    order = model.nextOrderString(optionOrders.get(optionOrders.size()-1));
                    optionOrders.add(order);
                }

                ((TextView)convertView.findViewById(R.id.order_number_text)).setText(order+":");
                ((TextView)convertView.findViewById(R.id.content)).setText(model.getSelectionAt(position));
            }
            return convertView;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            Spinner spinner = view.findViewById(R.id.correct_answer);

        }

    }
}
