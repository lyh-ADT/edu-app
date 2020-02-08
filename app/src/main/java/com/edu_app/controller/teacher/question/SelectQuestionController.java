package com.edu_app.controller.teacher.question;

import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.edu_app.model.teacher.addquestion.SelectQuestionItem;
import com.edu_app.model.teacher.practice.QuestionItem;

import java.util.ArrayList;

public class SelectQuestionController extends Controller {
    private SelectQuestionItem model;
    private ArrayList<String> optionOrders = new ArrayList<>();
    private boolean editable;

    public SelectQuestionController(View view, QuestionItem model, boolean editable) {
        super(view, model);
        this.model = (SelectQuestionItem) model;
        this.editable = editable;
        optionOrders.add("请选择");
        bindListener();
    }

    @Override
    protected void bindListener(){
        ListView option_list = view.findViewById(R.id.option_list_lv);
        final ListAdapter adapter = new ListAdapter();
        option_list.setAdapter(adapter);

        Button addOption = view.findViewById(R.id.add_option);
        if(editable){
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
        } else {
            addOption.setVisibility(View.GONE);
        }


        final Spinner correct_answer = view.findViewById(R.id.correct_answer);
        ArrayAdapter<String> a = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, optionOrders);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        correct_answer.setAdapter(a);
        if(editable){
            correct_answer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position == 0){
                        return;
                    }
                    model.setAnswer(optionOrders.get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
        } else {
            int index = model.getOrderByText(model.getAnswer());
            correct_answer.setSelection(index);
            correct_answer.setEnabled(false);
        }
    }

    private class ListAdapter extends BaseAdapter{
        private View.OnClickListener removeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView order_tv = v.findViewById(R.id.order_number_text);
                final String order = order_tv.getText().toString().substring(0, order_tv.getText().length()-1);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("删除选项")
                        .setCancelable(true)
                        .setMessage("删除选项"+order)
                        .setPositiveButton("取消", null)
                        .setNegativeButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int index = model.removeSelectionAt(order);
                                optionOrders.subList(index+1, optionOrders.size()).clear();
                                ListAdapter.this.notifyDataSetChanged();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };

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
                } else if(position+1 < optionOrders.size()){
                    order = optionOrders.get(position+1);
                } else {
                    order = model.nextOrderString(optionOrders.get(optionOrders.size()-1));
                    optionOrders.add(order);
                }

                ((TextView)convertView.findViewById(R.id.order_number_text)).setText(order+":");
                ((TextView)convertView.findViewById(R.id.content)).setText(model.getSelectionAt(position));
                convertView.setOnClickListener(removeListener);
            }
            return convertView;
        }
    }
}
